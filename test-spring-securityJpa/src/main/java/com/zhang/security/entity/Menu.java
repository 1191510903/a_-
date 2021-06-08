package com.zhang.security.entity;

import com.zhang.security.anotation.Description;
import com.zhang.security.utils.FBEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@ToString
@Getter
@Setter
@Entity
public class Menu extends FBEntity {
    @Description("上级菜单")
    @ManyToOne
    private Menu parentMenu;

    @Description("菜单名称")
    private String name;

    @Description("跳转地址")
    private String url;

    @Description("是否启用")
    @Column(name = "is_enable")
    private String isEnable;

    @Description("序号")
    @Column(name = "order_id")
    private Long orderId;

    @Description("菜单编号")
    @Column(name = "menu_code")
    private String menuCode;

    @Description("菜单父级编号")
    @Column(name = "menu_parent_code")
    private String menuParentCode;

}
