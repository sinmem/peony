package com.sinmem.peony.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.sinmem.peony.dao.mapper")
@ComponentScan(basePackages = {"com.sinmem.peony.web.controller", "com.sinmem.peony.web.conf", "com.sinmem.peony.service", "com.sinmem.peony.aop", "com.sinmem.peony.common.utils"})
@EnableTransactionManagement
public class PeonyApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PeonyApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(PeonyApplication.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
