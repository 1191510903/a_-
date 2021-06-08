package com.zhang.security.config;

import com.zhang.security.filter.AuthenticationTokenProcessingFilter;
import com.zhang.security.filter.LoginAuthenticationFilter;
import com.zhang.security.handler.LoginFailureHandler;
import com.zhang.security.handler.LoginSuccessHandler;
import com.zhang.security.handler.MyAuthenticationEntryPoint;
import com.zhang.security.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.GenericFilterBean;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final UserServiceImpl userService;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler, LogoutSuccessHandler logoutSuccessHandler, UserServiceImpl userService, MyAuthenticationEntryPoint myAuthenticationEntryPoint) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userService = userService;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用and()更简易
        // 禁用CSRF 开启跨域
        http.csrf().disable().exceptionHandling();

        // 自定义登录
        http.addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 表单登录
        http.formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                // 授权
                .and().authorizeRequests().
                anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll();
        //第6步：处理异常情况：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);
    }


    @Bean
    LoginAuthenticationFilter myAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailureHandler);
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


}
