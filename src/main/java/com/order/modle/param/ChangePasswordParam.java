package com.order.modle.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiakun
 * @Date: 2020/10/24 14:25
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改密码信息", description = "com.whstone.xfkbmanager.model.param.ChangePasswordParem")
public class ChangePasswordParam {
    @ApiModelProperty(value = "经RSA加密旧密码", example = "pq0s9R/Nydx3A=")
    private String oldPassword;
    @ApiModelProperty(value = "经RSA加密新密码", example = "Lapq0s9R/Nydx3A=")
    private String newPassword;
}
