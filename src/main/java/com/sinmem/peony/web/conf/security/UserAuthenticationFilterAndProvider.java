package com.sinmem.peony.web.conf.security;

import com.sinmem.peony.common.utils.GsonUtils;
import com.sinmem.peony.common.utils.RedisUtils;
import com.sinmem.peony.dao.bean.CodeBean;
import com.sinmem.peony.dao.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf.security
 * @Author sinmem
 * @CreateTime 2019-12-20 21:14
 * @Description security Configs
 */
@Component
public class UserAuthenticationFilterAndProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final String MOBILE_PARAMETER = "verificationCode";
    private static final String LOGIN_HEAD = "LOGIN_";
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 获取登录提交的用户名和密码
        String password = (String) authentication.getCredentials();

        // 获取登录提交的验证码
        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
        String validateCode = details.getVerificationCode();

        User userInfo = (User)userDetails;
        // 验证码校验
//        codeValidate(validateCode, userInfo.getPhoneNumber());

        // 验证用户名密码
        if (userInfo != null) {
            if (!passwordEncoder.matches(password, userInfo.getPassword())) {
                throw new BadCredentialsException("用户名或密码错误");
            }else {
                // 登陆后删除验证码缓存
                redisUtils.del(LOGIN_HEAD+userInfo.getPhoneNumber());
            }
            // //这里还可以加一些其他信息的判断，比如用户账号已停用等判断，这里为了方便我接下去的判断，我就不用加密了。
        }else {
            throw new BadCredentialsException("用户名或密码错误");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userDetailsService.loadUserByUsername(username);
    }

    // 是否支持的类型
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    private void codeValidate(String validateCode, String phoneNumber) {
        if(!StringUtils.hasText(validateCode)){
            throw new BadCredentialsException("请输入验证码");
        }
//        String key = LOGIN_HEAD+phoneNumber;
        String resultCache = redisUtils.getStr(LOGIN_HEAD+phoneNumber);
        // 防止重复提交获取验证码请求
        if(resultCache == null || "null".equals(resultCache)){
            // 如果当前缓存中没有该用户的该类验证码, 则继续执行
            throw new BadCredentialsException("未获取验证码或验证码已失效");
        }else {
            String smsCode = GsonUtils.fromJson(resultCache, CodeBean.class).getCode();
            // 登陆后删除验证码缓存
//            redisUtils.del(key);
            if(StringUtils.hasText(smsCode) && smsCode.equals(validateCode)){
                // 继续执行认证
                return;
            }
            throw new BadCredentialsException("验证码错误");
        }

    }
}
