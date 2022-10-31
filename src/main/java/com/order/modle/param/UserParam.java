package com.order.modle.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: jiakun
 * @Date: 2020/10/22 20:18
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户信息")
public class UserParam {

    @ApiModelProperty(value = "用户名", example = "jktest")
    private String userName;

    @ApiModelProperty(value = "登录账户", example = "jktest")
    private String loginName;

    @ApiModelProperty(value = "经RSA加密密码", example = "0wVx5SLapq0s9R/Nydx3A=")
    private String password;

    @ApiModelProperty(value = "角色Id", example = "1")
    private String roleCode;
    /**
     */
    @ApiModelProperty(value = "状态", example = "enable")
    private String status;
}
