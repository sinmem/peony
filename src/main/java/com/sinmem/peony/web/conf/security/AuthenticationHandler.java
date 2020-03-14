package com.sinmem.peony.web.conf.security;

import com.sinmem.peony.common.Result;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.utils.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf.security
 * @Author sinmem
 * @CreateTime 2019-12-22 14:27
 * @Description
 */
@Component
public class AuthenticationHandler implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {
    private static final int ONE_MONTH = 0x278d00;

    // 需要登录
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServletUtils.responseJson(response, Result.error(Msg.E40001).setContent(e.getMessage()));
    }

    // 没有权限
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
//        response.setStatus(200);
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().append(Result.error(Msg.E40003).toString());
        ServletUtils.responseJson(response, Result.error(Msg.E40003).setContent(e.getMessage()));
    }

    // 登录失败
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        if(isAjaxRequest(request)){
//            System.out.println("this is ajaxRequest");
//            response.setStatus(200);
//            response.setContentType("application/json;charset=UTF-8");
//            response.getWriter().print(Result.error(Msg.E40002).toString());
//            response.getWriter().flush();
//        }
        ServletUtils.responseJson(response, Result.error(Msg.E40002).setContent(e.getMessage()));
    }

    // 登录成功
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        HttpSession session = request.getSession();
//        // 设置Session时效为30天
//        session.setMaxInactiveInterval(ONE_MONTH);
//        response.setHeader("X-Auth-Token", session.getId());
//        if(isAjaxRequest(request)){
//            System.out.println("this is ajaxRequest");
//            response.setStatus(200);
//            response.setContentType("application/json;charset=UTF-8");
//            Object object = authentication.getPrincipal();
//            response.getWriter().print(Result.success(object).toString());
//            response.getWriter().flush();
//        }
//        Object object = authentication.getPrincipal();
        ServletUtils.responseJson(response, Result.success("登陆成功"));
    }

    // 登出成功
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletUtils.responseJson(response, Result.success());
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}
