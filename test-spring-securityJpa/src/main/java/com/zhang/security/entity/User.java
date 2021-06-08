package com.zhang.security.entity;


import com.zhang.security.anotation.Description;
import com.zhang.security.utils.FBEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
public class User  extends UserDetail {

    @Description("角色")
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    Collection<Role> roles;

    // 默认为空表示启用，“1”表示删除
    @Description("启用删除标志")
    @Column(name = "flag_of_delete")
    private String flagOfDelete;

    @Description("工号")
    @Column(name = "work_id")
    private String workId;

    @Description("手机号")
    private String mobile;

    @Description("座机号")
    private String phone;
//    @Description("机构")
//    @OneToOne
//    private Institution org;
    @Description("邮箱地址")
    private String email;

    @Description("用户姓名")
    private String username;

    @Description("开始时间")
    @Column(name = "start_date")
    private Date startDate;

    @Description("停用时间")
    @Column(name = "stop_date")
    private Date stopDate;

}
