package com.zhang.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 枚举-角色类型
 * @Author zyk
 * @Date 2021-04-06
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    SUPER("0","超级管理员",0)
    ,SYSTEM("1","系统管理员",1)
    , SUBSYSTEM("2","业务条线管理员",2)
    , CUSTOM("41","内置普通角色",5);
    private String roleType;
    private String roleName;
    private int roleLevel;
}
