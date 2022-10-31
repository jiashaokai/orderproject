package com.order.modle.entity;


import com.order.constant.rbac.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户信息表
 *
 * @Author: wanqian
 * @Date: 2020/10/21 16:18
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
public class UserInfoDO implements UserDetails {

    /**
     * 主键
     */
    @Id
    @TableGenerator(name = "ID_GENERATOR", table = "ID_GENERATOR",
            pkColumnName = "PK_NAME", pkColumnValue = "USER_ID",
            valueColumnName = "PK_VALUE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
    @Column(columnDefinition = "INT COMMENT '用户-主键'")
    private Integer userId;

    /**
     * 用户名
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '用户名'")
    private String userName;

    /**
     * 用户名
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '用户名'")
    private String loginName;

    /**
     * 密码
     */
    @Column(columnDefinition = "VARCHAR(512) COMMENT '用户名'")
    private String password;

    /**
     * 用户角色id
     */
    @Column(columnDefinition = "VARCHAR(20) COMMENT '用户角色id'")
    private String roleCode;

    /**
     * 状态
     */
    @Column(columnDefinition = "VARCHAR(20) COMMENT '状态'")
    private String status;

    /**
     * 创建时间
     */
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private Date createTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(roleCode));

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        if ("enable".equals(status)) {
            return true;
        }
        return false;
    }
}
