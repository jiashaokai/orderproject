package com.order.constant.rbac;

/**
 * 用户角色
 *
 * @Author: wanqian
 * @Date: 2020/10/21 15:53
 */
public enum UserRoleEnum {

    SUPER_ADMIN(1, "super_admin", "超级管理员"),
    USER(2, "user", "普通用户");

    public Integer roleId;
    public String roleCode;
    public String roleName;

    UserRoleEnum(Integer roleId, String roleCode, String roleName) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public static UserRoleEnum getByRoleCode(String roleCode) {
        for (UserRoleEnum value : UserRoleEnum.values()) {
            if (value.roleCode.equalsIgnoreCase(roleCode)) {
                return value;
            }
        }
        return null;
    }

    public static UserRoleEnum getByRoleCode(Integer roleId) {
        for (UserRoleEnum value : UserRoleEnum.values()) {
            if (value.roleId.equals(roleId)) {
                return value;
            }
        }
        return null;
    }
}
