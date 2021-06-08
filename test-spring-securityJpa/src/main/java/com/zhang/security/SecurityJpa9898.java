package com.zhang.security;

import com.zhang.security.filter.AuthenticationTokenProcessingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SecurityJpa9898 {
    public static void main(String[] args) {
        SpringApplication.run(SecurityJpa9898.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AuthenticationTokenProcessingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("authFilter");
        registration.setOrder(1);
        return registration;
    }

}


