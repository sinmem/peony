package com.sinmem.peony.service.serviceImpl;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.AccountStatus;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.enums.CodeType;
import com.sinmem.peony.common.utils.AliSmsSendUtils;
import com.sinmem.peony.common.utils.GsonUtils;
import com.sinmem.peony.common.utils.RandomUtils;
import com.sinmem.peony.common.utils.RedisUtils;
import com.sinmem.peony.dao.bean.CodeBean;
import com.sinmem.peony.dao.bean.Role;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.mapper.RoleMapper;
import com.sinmem.peony.dao.mapper.UserMapper;
import com.sinmem.peony.dao.mapper.UserPointsMapper;
import com.sinmem.peony.service.UserPointService;
import com.sinmem.peony.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.ValidationException;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service.serviceImpl
 * @Author sinmem
 * @CreateTime 2019-12-22 00:58
 * @Description 用户详细信息业务实现类
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final int REGISTER = 1;
    private static final int LOGIN = 2;
    private static final int RESET_PWD = 3;
    private static final int OTHER = 4;
    private static final User falseUser = new User("", "", AccountStatus.LOCKED, false);

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserPointsMapper pointsMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPointService userPointService;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userMapper.getUserByPhone(phoneNumber);
        user = user == null ? falseUser : user;
        user.setRoles(roleMapper.getRolesByUserIdL(user.getId()));
        return user;
    }

    @Override
    @Transactional
    public Result register(User user, String validateCode, String inviteCode) {
        // 如果用户已经注册就返回
        if (userMapper.getUserIdByPhone(user.getPhoneNumber()) != null) {
            return Result.error(Msg.E40006);
        }

        // 这里 key = REGISTER表示传递的验证码类型为: 注册
        codeValidate(user.getPhoneNumber(), validateCode, REGISTER);

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 添加用户记录同时增加该用户总积分记录, 默认为用户增加普通用户权限
        if (userMapper.addUser(user) == 1 && pointsMapper.insertPoint(user) == 1 && roleMapper.addUserByUserId(user.getId(), Role.ROLE_USER) == 1) {
            // 如果注册时候顺便填写邀请码则执行邀请业务逻辑,
            // 这个逻辑一个是在用户注册成功后此决定执行的
            if (StringUtils.hasText(inviteCode)) {
                Result doInviteResult = userPointService.doInvite(user, inviteCode);
                // 如果用户注册成功的同时邀请码填写失败, 注册需要照常成功,
                // 但是需要告知用户,但验证码填写失败了
                if (doInviteResult.getCode() != 0) {
                    return Result.success("用户注册成功, 但邀请码填写失败! 如需重新填写, 可以在用户-积分-邀请获得积分中进行. 失败原因: " + doInviteResult.getMessage());
                }
            }
            codeDel(CodeType.getValue(REGISTER) + user.getPhoneNumber());
            return Result.success("用户注册成功");
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E20006).setMessage("用户注册失败");
        }
    }

    @Override
    public Result getVerifyCode(String phoneNumber, Integer type) {
        String resultCache = redisUtils.getStr(CodeType.getValue(type) + phoneNumber);
        // 防止重复提交获取验证码请求
        if (resultCache == null || "null".equals(resultCache)) {
            // 如果当前缓存中没有该用户的该类验证码, 则继续执行
        } else {
            // 否则继续判断该类验证码是不是刚创建不超过1分钟,
            // 未超过则直接返回操作过于频繁
            CodeBean tempCode = GsonUtils.fromJson(resultCache, CodeBean.class);
            long a = System.currentTimeMillis() - tempCode.getCreateTime();
            if (a < (1000 * 60)) {
                return Result.error(Msg.E10001).setMessage("获取验证码失败, 操作过于频繁! 请留意使用已到达的验证码, 或者1分钟后重试!");
            }
        }
        // 校验是应该发送验证码
        if (userMapper.getUserIdByPhone(phoneNumber) != null) {
            // 如果手机号已被注册,那么不可以在用来发送注册验证码
            if (type == 1) {
                return Result.error(Msg.E40005).setMessage("手机号已被注册");
            } else {// 如果需要其他类型的验证码, 那么必须是注册用户
                return sendVerifyCode(phoneNumber, type);
            }
        } else {
            // 如果手机号未被注册,那么可以在用来发送注册验证码
            if (type == 1) {
                return sendVerifyCode(phoneNumber, type);
            }
        }
        return Result.error(Msg.E40005).setMessage("未查找到该用户");
    }

    @Override
    public Result reset(User user, String validateCode) {
        codeValidate(user.getPhoneNumber(), validateCode, RESET_PWD);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userMapper.updatePWDByPhone(user) == 1) {
            codeDel(CodeType.getValue(RESET_PWD) + user.getPhoneNumber());
        } else {
            return Result.error(Msg.E11111).setMessage("未知原因到时重置密码失败");
        }
        return Result.success("重置密码成功");
    }

    @Override
    public Result forgot(User user, String validateCode) {
        return reset(user, validateCode);
    }

    @Override
    public Result updateUsername(User user, String username) {
        user.setUsername(username);
        userMapper.updateUserName(user);
        return Result.success("用户名修改成功");
    }

    /**
     * 发送验证码
     *
     * @param phoneNumber 收到验证码的手机
     * @param type        验证码类型 &nbsp;[(1,"REGISTER_"), (2,"LOGIN_"), (3,"RESET_PWD_"), (4,"OTHER_")]
     * @return
     */
    private Result sendVerifyCode(String phoneNumber, Integer type) {
        // 生成验证码
        String verifyCode = RandomUtils.getInstance().getNumRandomCode();
        // 存储验证码服务器缓存
        CodeBean tempCode = new CodeBean(verifyCode, System.currentTimeMillis());
        redisUtils.set(CodeType.getValue(type) + phoneNumber, GsonUtils.toJson(tempCode), 30 * 60);
        // 调用短信接口发送验证码
        AliSmsSendUtils.sendSms(phoneNumber, verifyCode);
        return Result.success("验证码已发送成功,请在30分钟内使用, 验证码: " + tempCode);
    }

    /**
     * 验证手机验证码(对于数据的安全校验有调用者解决,此处默认传入数据正确)<br>
     * 它不需要返回值, 因为除了成功的情况都直接抛出了对应异常, 只有成功可以继续执行
     *
     * @param phoneNumber  手机号
     * @param validateCode 验证码
     * @param type         验证码类型
     * @return
     */
    private void codeValidate(String phoneNumber, String validateCode, Integer type) {
        String key = CodeType.getValue(type) + phoneNumber;
        String resultCache = redisUtils.getStr(CodeType.getValue(type) + phoneNumber);
        // 防止重复提交获取验证码请求
        if (resultCache == null || "null".equals(resultCache)) {
            // 如果当前缓存中没有该用户的该类验证码, 则继续执行
            throw new ValidationException("未获取验证码或验证码已失效");
        } else {
            String smsCode = GsonUtils.fromJson(resultCache, CodeBean.class).getCode();
            // 删除验证码缓存, 是该验证码失效
//            codeDel(key);
            if (StringUtils.hasText(smsCode) && smsCode.equals(validateCode)) {
                // 继续执行认证
                return;
            }
            throw new ValidationException("验证码错误");
        }
    }

    private void codeDel(String key) {
        redisUtils.del(key);
    }
}
