package com.sinmem.peony.web.conf.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf.security
 * @Author sinmem
 * @CreateTime 2019-12-29 16:09
 * @Description 自动以用户详情
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private static final String MOBILE_PARAMETER = "verificationCode";
    private final String verificationCode;
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // 拿页面传来的验证码
        verificationCode = request.getParameter(MOBILE_PARAMETER);
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
