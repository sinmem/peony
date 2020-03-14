package com.sinmem.peony.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.ResultPage;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.enums.PointType;
import com.sinmem.peony.common.exception.ValidationException;
import com.sinmem.peony.common.utils.RandomUtils;
import com.sinmem.peony.dao.bean.PointDetail;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.dao.dto.InvitedDetailDto;
import com.sinmem.peony.dao.dto.LawBriefDto;
import com.sinmem.peony.dao.mapper.UserInvitedMapper;
import com.sinmem.peony.dao.mapper.UserMapper;
import com.sinmem.peony.dao.mapper.UserPointsMapper;
import com.sinmem.peony.dao.mapper.UserVipInfoMapper;
import com.sinmem.peony.service.UserPointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.service.serviceImpl
 * @Author sinmem
 * @CreateTime 2020-01-04 18:18
 * @Description
 */
@Service
public class UserPointServiceImpl implements UserPointService {
    public static final int DEFAULT_SIZE = 10;
    public static final int VIP_MULT = 3;
    public static final int SVIP_MULT = 5;

    private static final int INVITE_BONUS = 10;
    private static final int DEFAULT_CODE_LENGTH = 6;
    @Resource
    private UserPointsMapper userPointsMapper;
    @Resource
    private UserInvitedMapper userInvitedMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserVipInfoMapper userVipInfoMapper;

    ///////////////// 注: 禁止对此处所传参的user对象进行任何改动, 它是session唯一的标志登录用户的对象 ////////////
    ///////////////// 除了复制以外的任何操作都只允许对他的副本进行 /////////////////////////////////////////////
    @Override
    public Result getUserPoints(User user) {
        Integer pointsCount = userPointsMapper.getUserPointCount(user);
        if(pointsCount == null){
            return Result.error(Msg.E40011).setMessage("获取积分失败");
        }
        return Result.success().setContent(pointsCount);
    }

    @Override
    public Result getPointsDetail(User user, Integer pageNum) {
        Page<PointDetail> page = PageHelper.startPage(pageNum,DEFAULT_SIZE).doSelectPage(()->userPointsMapper.queryPointDetails(user));
        return Result.success().setContent(new ResultPage(page));
    }

    @Override
    public Result getPointsPayDetail(User user, Integer pageNum) {
        Page<PointDetail> page = PageHelper.startPage(pageNum,DEFAULT_SIZE).doSelectPage(()->userPointsMapper.queryPointPayDetails(user));
        return Result.success().setContent(new ResultPage(page));
    }

    @Override
    public Result getPointsGetDetail(User user, Integer pageNum) {
        Page<PointDetail> page = PageHelper.startPage(pageNum,DEFAULT_SIZE).doSelectPage(()->userPointsMapper.queryPointGetDetails(user));
        return Result.success().setContent(new ResultPage(page));
    }

    @Override
    @Transactional
    public Result addUserPoints(User user, Integer number, PointType pointType) {
        // 装载积分详情类用来更新积分总表和插入详情表
        number = number<0 ? -number:number;
        Integer pointCount = userPointsMapper.getUserPointCount(user);
        if(pointCount == null){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("用户数据错误, 不存在积分记录!");
        }
        if(pointCount < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("积分数额错误, 增加积分操作失败!");
        }
        PointDetail temp = new PointDetail();
        temp.setUser(user);
        temp.setPointsDetail(number);
        temp.setPointsCount(pointCount + number);
        temp.setDetailType(pointType);
        if(userPointsMapper.updateUserPoint(temp) != 1 || userPointsMapper.addPoints(temp) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("增加积分操作失败");
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result subUserPoint(User user, Integer number, PointType pointType) {
        // 装载积分详情类用来更新积分总表和插入详情表
        number = number>0 ? -number:number;
        Integer pointCount = userPointsMapper.getUserPointCount(user);
        if(pointCount == null){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("用户数据错误, 不存在积分记录!");
        }
        if(pointCount < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("积分数额错误, 扣除积分失败!");
        }if(pointCount+number < 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("积分额度不足, 扣除积分失败!");
        }
        PointDetail temp = new PointDetail();
        temp.setUser(user);
        temp.setPointsDetail(number);
        temp.setPointsCount(pointCount + number);
        temp.setDetailType(pointType);
        if(userPointsMapper.updateUserPoint(temp) != 1 || userPointsMapper.addPoints(temp) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40008).setMessage("扣除积分失败");
        }
        return Result.success();
    }

    @Override
    public Result getInviteCode(User user) {
        String code = null;
        if((code = userInvitedMapper.getCodeIdByUserId(user.getId())) == null){
            //生成邀请码
            code = RandomUtils.getInstance().getRandomCode(user.getId(),DEFAULT_CODE_LENGTH);
            if(userInvitedMapper.insertInviteCode(user.getId(), code)!=1){
                return Result.error(Msg.E20000).setMessage("生成验证码失败");
            }
        }
        return Result.success().setContent(code);
    }

    @Override
    @Transactional
    public Result doInvite(User user, String inviteCode) {
        // 检查填写验证码的用户是否已经填写过验证码
        if(isInvited(user) != null){
            return Result.error(Msg.E40007).setMessage("已经填写过邀请码");
        }
        // 新增邀请记录
        Long inviterId = userInvitedMapper.getInviterIdByCode(inviteCode);
        if(inviterId == null){
            return Result.error(Msg.E40007).setMessage("邀请码填写错误");
        }
        if(inviterId.equals(user.getId())){
            return Result.error(Msg.E40007).setMessage("无法将自己作为受邀人填写邀请码");
        }
        if(userInvitedMapper.addInvitedDetail(user, inviterId) != 1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.error(Msg.E40007).setMessage("填写邀请码操作失败");
        }
        // 将id变更为邀请者的id, 然后为他增加积分
        User tempUser = new User();
        tempUser.setId(inviterId);
        tempUser.setPhoneNumber(user.getPhoneNumber());
        Result addUserPointsResult = addUserPoints(tempUser, INVITE_BONUS, PointType.INVITE);

        if(addUserPointsResult.getCode() != 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return addUserPointsResult;
        }
        return Result.success("邀请码填写成功");
    }

    @Override
    public User isInvited(User user) {
        Long inviterId = null;
        if((inviterId = userInvitedMapper.isInvited(user.getId())) != null){
            User temp = userMapper.getUserById(inviterId);
            temp.setPassword(null);
            return temp;
        }
        return null;
    }

    @Override
    public Result getInvitedList(User user, Integer pageNum) {
        Page<InvitedDetailDto> page = PageHelper.startPage(pageNum,DEFAULT_SIZE).doSelectPage(()->userInvitedMapper.getInvitedList(user.getId()));
        return Result.success(new ResultPage(page));
    }

    @Override
    public Result getSVip(User user, Integer time) {
        pointsValidate(user,time,SVIP_MULT);
        if(hasVip(user)){
            //扣除积分,变更角色,记录VIP

        }else {

        }
        return Result.error(Msg.E11111).setMessage("未完全实现, 现屏蔽");
    }

    @Override
    public Result getVip(User user, Integer time) {
        pointsValidate(user,time,VIP_MULT);
        if(hasVip(user)){

        }else {

        }
        return Result.error(Msg.E11111).setMessage("未完全实现, 现屏蔽");
    }

    private boolean hasVip(User user){
        return userVipInfoMapper.getUserVipInfo(user)==null;
    }

    private void pointsValidate(User user, int time, int mult){
        int count = time*mult;
        Integer result = userPointsMapper.getUserPointCount(user);
        if(result == null || result<0){
            throw new ValidationException(Msg.E20000).setMessage("用户积分数据");
        }else if((result-count)<0){
            throw new ValidationException(Msg.E40011).setMessage("用户积分余额不足");
        }
    }
}
