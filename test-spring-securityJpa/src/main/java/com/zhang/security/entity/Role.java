package com.zhang.security.entity;

import com.zhang.security.anotation.Description;
import com.zhang.security.enums.RoleEnum;
import com.zhang.security.utils.FBEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;


/**
 * 角色表
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "role")
public class Role extends FBEntity {
    @Description("角色名称")
    @Column(name = "role_name")
    private String roleName;

    @Description("role表的标记，用于与临时表数据做区分")
    @Column(name="is_using" ,nullable=true,columnDefinition="INT default 1")
    private int isusing;

    @Description("存值用")
    @Column(name = "roleid")
    private Long roleId;
    // 审核状态
    @Description("审核状态值 0:未提交，1：待审核，2：审核通过，3：审核拒绝")
    @Column(name = "audit_status")
    private String auditStatus;


    // 操作状态
    @Description("操作状态值 a：新建，b：修改 c：删除 d:无")
    @Column(name = "operation_status")
    private String operationStatus;

    @Description("角色类型 0：超级管理员、1：系统管理员、2：业务条线管理员 3.操作員")
    @Enumerated(EnumType.ORDINAL)
    private RoleEnum roleType;


    @Description("菜单")
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy("orderId ASC")
    private Collection<Menu> menu;

}
