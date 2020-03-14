package com.sinmem.peony.web.conf.security;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf.security
 * @Author sinmem
 * @CreateTime 2019-12-29 16:09
 * @Description
 */

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 在Spring Security登录过程中对用户的登录信息的详细信息进行填充
 */
@Component("authenticationDetailsSource")
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest httpRequest) {
        return new CustomWebAuthenticationDetails(httpRequest);
    }
}
