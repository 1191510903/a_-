package com.zhang.security.service.impl;

import com.zhang.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final PasswordEncoder pw;

    @Autowired
    public UserServiceImpl(PasswordEncoder pw) {
        this.pw = pw;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("==================进入自定义逻辑==================");
        System.out.println("==================userName=============="+username);
        if(username.isEmpty()){
            throw new UsernameNotFoundException("用户名为空");
        }
        User user = new User();
        user.setLoginId("superadmin");
        user.setUsername("superadmin");

        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        System.out.println("=========================user:==========="+user);
        return user;
    }
}
