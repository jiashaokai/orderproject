package com.order.constant;

public enum ResponseSuccessEnum {

    LOGIN_SUCCESS("登陆成功", 200),
    LOGINOUT_SUCCESS("登出成功", 200),
    QUERY_SUCCESS("查询成功", 200),
    OPERATION_SUCCESS("操作成功", 200),
    QUERY_SUCCESS_NODATA("查无数据", 200),
    CREATE_SUCCESS("创建成功", 200),
    UPDATE_SUCCESS("修改成功", 200),
    BINDING_SUCCESS("绑定成功", 200),
    VALIDATE_SUCCESS("验证成功", 200),
    ACTIVATE_SUCCESS("激活成功", 200),
    DELETE_SUCCESS("删除成功", 200);


    public String name;
    public Integer code;

    ResponseSuccessEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    ResponseSuccessEnum(String name) {
        this.name = name;
    }
}
