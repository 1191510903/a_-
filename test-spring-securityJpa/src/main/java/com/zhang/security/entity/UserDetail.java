package com.zhang.security.entity;

import com.zhang.security.anotation.Description;
import com.zhang.security.utils.FBSubject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Collection;

@MappedSuperclass
public class UserDetail extends FBSubject implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;
    @Description("登录Id")
    private String loginId;
    @Description("登录密码")
    private String password;

    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String getUsername() {
        return loginId;
    }

}