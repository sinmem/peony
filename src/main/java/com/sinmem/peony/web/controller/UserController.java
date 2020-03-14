package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.ValidationException;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.controller
 * @Author sinmem
 * @CreateTime 2019-12-29 11:28
 * @Description
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/user/info/updateUsername")
    public String updateUsername(User user, String username){
        return userService.updateUsername(user, username).toString();
    }

    @PostMapping("/user/auth/forgot")
    public String forgot(User user, String verificationCode){
        return userService.forgot(user, verificationCode).toString();
    }

    @PostMapping("/user/auth/reset")
    public String reset(User user, String verificationCode){
        return userService.reset(user, verificationCode).toString();
    }


    @PostMapping("/user/auth/register")
    public String register(User user, String verificationCode, String inviteCode){
        // 数据校验
        if(!StringUtils.hasText(verificationCode))
            throw new ValidationException("在验证码不能为空");
        if(user == null)
            throw new ValidationException("用户信息为空");
        validatePhoneNumber(user.getPhoneNumber());
        if(!StringUtils.hasText(user.getPassword()))
            throw new ValidationException("密码不能为空");
        // 如果验证码有非空内容, 则去掉首尾空格否则为空
        inviteCode = StringUtils.hasText(inviteCode) ? inviteCode.trim() : null;
        return userService.register(user, verificationCode, inviteCode).toString();
    }

    /**
     * 用户获取短信验证码
     * @param phoneNumber 收到验证码的手机
     * @param type 验证码类型[(1,"REGISTER_"),(2,"LOGIN_"),(3,"RESET_PWD_"),(4,"OTHER_")]
     * @return
     */
    @GetMapping("/user/auth/getVerifyCode")
    public String getVerifyCode(String phoneNumber, Integer type){
        validatePhoneNumber(phoneNumber);
        if(type<1 || type > 5){
            throw new ValidationException("验证码类型错误");
        }
        return userService.getVerifyCode(phoneNumber, type).toString();
    }

//    private void validateVerificationCode(String verificationCode){
//        if(!StringUtils.hasText(verificationCode))
//            throw new ValidationException("手机号不能为空");
//    }

    private void validatePhoneNumber(String phoneNumber){
        if(!StringUtils.hasText(phoneNumber))
            throw new ValidationException("手机号不能为空");
        if(phoneNumber.trim().length() != 11)
            throw new ValidationException("请输入正确的手机号");
    }

}
