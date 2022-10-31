package com.order.modle.VO.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: jiakun
 * @Date: 2020/10/23 17:09
 * @Description
 */
@Data
@ApiModel(value = "RSA加密公钥信息", description = "com.whstone.xfkbmanager.model.VO.user.PublicKeyVO")
public class PublicKeyVO {

    @ApiModelProperty(value = "RSA公钥", example = "sadgsdfsdcxvdfasf")
    private String publicKey;
}
