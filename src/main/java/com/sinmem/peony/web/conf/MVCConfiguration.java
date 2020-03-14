package com.sinmem.peony.web.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @BelongsProject: peony
 * @BelongsPackage: com.sinmem.peony.web.conf
 * @Author: sinmem
 * @CreateTime: 2019-10-19 10:36
 * @Description: 视图配置
 * @version: V1.0
 */
@Configuration
public class MVCConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
