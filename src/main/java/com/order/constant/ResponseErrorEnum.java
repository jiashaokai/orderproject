package com.order.constant;

/**
 * Created by chenzheqi on 2017/5/24.
 *
 * @Description 错误编码为 aa+bbb模式 aa-模块代码 由10-99 bbb-在aa模块下的错误码
 */
public enum ResponseErrorEnum {

    /**
     * 未捕获的异常错误
     */
    UNKOWN_ERROR("服务正在开小差，请查看服务端错误日志", 00001),

    ///////////////////////////
    // 控制层请求错误(10000-10999) //
    //////////////////////////
    PARAM_INVALID("请求参数无效", 10001),
    PARAM_BLACK("请求参数不能为空", 10002),
    PARAM_TYPE_ERROR("请求参数类型错误", 10003),
    PARAM_NOT_COMPLETE("缺少请求参数", 10004),

    ///////////////////////////
    // 用户权限模块(20000-20999) //
    //////////////////////////
    USER_NOT_EXIST("用户不存在", 20001),
    USER_PASSWORD_ERROR("用户原始密码错误", 20002),

    USER_ACCOUNT_FORBIDDEN("登录账号被管理员禁用", 20003),
    USER_IS_EXISTED("用户已存在", 20004),
    AUTH_EXPIRE("需要重新登录", 20005),
    INSUFFICIENT_PERMISSIONS("权限不足", 20006),
    AUTHENTICATION_ERROR("身份验证异常", 20007),
    DECODE_ERROR("密码解密失败", 20008),
    TOKEN_ERROR("错误的token",20009),
    USER_VERIFY_PASSWORD_ERROR("用户确认密码错误", 20010),

    EXIST_SAME_HOST_GROUP("存在同名主机组", 30001),
    HOST_GROUP_NOT_EXIST("主机组不存在", 30002),

    EXIST_SAME_USER_GROUP("存在同名用户组", 40001),
    USER_GROUP_NOT_EXIST("用户组不存在", 40002),

    TEST("测试", 50000);
    public String name;
    public Integer code;

    ResponseErrorEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    /**
     * @desc: 根据code获取错误枚举
     * @author: wanqian
     * @date: 2020/5/16 14:57
     * @param: [code]
     * @return: com.whstone.xfybcommon.constant.restfull.ResponseErrorEnum
     */
    public static ResponseErrorEnum getErrorEnum(Integer code) {
        for (ResponseErrorEnum errorEnum : ResponseErrorEnum.values()) {
            if (errorEnum.code.equals(code)) {
                return errorEnum;
            }
        }
        return UNKOWN_ERROR;
    }
}
