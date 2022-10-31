package com.order.modle.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: jiakun
 * @Date: 2020/11/12 10:36
 * @Description
 */
@Data
@ApiModel(value = "修改个人信息参数", description = "com.whstone.xfkbmanager.model.param.ModifyPersonalInfoParam")
public class ModifyPersonalInfoParam {
    @ApiModelProperty(value = "用户名", example = "test")
    private String userName;
    @ApiModelProperty(value = "告警发送类型", example = "email/sms/wechat")
    private String alarmType;
    @ApiModelProperty(value = "微信号", example = "13419564789")
    private String wechat;
    @ApiModelProperty(value = "邮箱", example = "XXXX@qq.com")
    private String email;
    @ApiModelProperty(value = "手机号", example = "13419564789")
    private String phone;
    @ApiModelProperty(value = "经RSA加密新密码", example = "Vx5SLapq0s9R/Nydx3A=")
    private String password;
    @ApiModelProperty(value = "部门", example = "development")
    private String department;
}
