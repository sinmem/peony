package com.sinmem.peony.aop;

import com.sinmem.peony.dao.bean.User;
import com.sinmem.peony.web.conf.security.AuthenticationHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.aop
 * @Author sinmem
 * @CreateTime 2020-01-04 13:59
 * @Description 获取用户的切面
 */
@Aspect
@Component
public class GetUserAspect {
    @Pointcut("execution(public * com.sinmem.peony.web.controller.UserPointsController.*(..))||" +
            "execution(public * com.sinmem.peony.web.controller.UserController.updateUsername(..))||" +
            "execution(public * com.sinmem.peony.web.controller.LegalCaseController.agreeCase(..))||"+
            "execution(public * com.sinmem.peony.web.controller.LegalCaseController.disagreeCase(..))")
    public void getUser(){}

    @Around("getUser()")
    public Object getUserByRequest(ProceedingJoinPoint pjp) throws Throwable {
        Object[] objs = pjp.getArgs();
        Object[] params = new Object[objs.length];
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            if(token.getPrincipal() instanceof User){
                params[0] = token.getPrincipal();
            }
        }else {
            params[0] = objs[0];
        }
//        if (authentication == null){
//            params[0] = null;
//        }else {
//            params[0] = authentication.getPrincipal();
//        }
        System.arraycopy(objs,1,params,1,objs.length-1);
        return pjp.proceed(params);
    }
}
