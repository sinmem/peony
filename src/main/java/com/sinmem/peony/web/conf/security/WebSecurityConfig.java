package com.sinmem.peony.web.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.awt.*;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.web.conf.security
 * @Author sinmem
 * @CreateTime 2019-12-20 21:14
 * @Description security Configs
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL = "/user/auth/login.html";
    @Resource
    private UserAuthenticationFilterAndProvider provider;
    @Autowired
    private AuthenticationDetailsSource authenticationDetailsSource;
    @Resource
    private AuthenticationHandler authenticationHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers().permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登陆页
            .formLogin()
                .loginPage(LOGIN_URL)
                .loginProcessingUrl("/login")
                .permitAll()
                .successHandler(authenticationHandler)
                .failureHandler(authenticationHandler)
                .usernameParameter("phone")
                .passwordParameter("password")
                .authenticationDetailsSource(authenticationDetailsSource);
//                .and()
//            .logout().permitAll().and();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/",
                "/img/**",
                "/user/member-center.html",
                "/user/center/**",
                "/user/auth/**",
                "/css/**",
                "/js/**",
                "/plugins/**",
                "favicon.ico",
                "/error.html",
                "/404.html",
                "/abuilding.html",
                "/ueditor/**");
    }

}
