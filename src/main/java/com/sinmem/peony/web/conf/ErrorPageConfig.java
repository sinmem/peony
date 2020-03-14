package com.sinmem.peony.web.conf;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf
 * @Author sinmem
 * @CreateTime 2020-02-25 16:05
 * @Description 错误页面配置
 */
@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar{
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.html");
        registry.addErrorPages(error404, error500);
    }
}
