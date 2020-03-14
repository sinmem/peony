package com.sinmem.peony.web.controller;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.exception.BizException;
import com.sinmem.peony.dao.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.conf
 * @Author: sinmem
 * @CreateTime: 2019-10-20 17:06
 * @Description: 页面索引控制器
 * @version: V1.0
 */
@Controller
@CrossOrigin
public class IndexController {
//    @GetMapping("/")
//    public String home(){
//        return "home.html";
//    }

    @RequestMapping("/toLogin")
    public String showLogin() {
        return "login.html";
    }

    @RequestMapping("/login?error")
    public @ResponseBody String loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(exception != null) {
//            response.getWriter().write(exception.toString());
            return Result.error(Msg.E40002).setContent(exception.getMessage()).toString();
        }else {
            return Result.error(Msg.E11111).toString();
        }
    }


    @RequestMapping("web/v1/testError")
    public String testError(HttpServletRequest request) {
        return Result.success(request.toString()).toString();
    }

    // 个人信息
    @GetMapping("/me")
    public @ResponseBody Result me(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            if(token.getPrincipal() instanceof User){
                ((User) token.getPrincipal()).setPassword(null);
            }
            return Result.success(token.getPrincipal());
        }
        return Result.success(principal);
    }
}
