package com.order.modle.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wanqian
 * @Date: 2020/10/21 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Token信息",description = "com.whstone.xfkbmanager.model.VO.user.TokenVO")
public class TokenVO {


    @ApiModelProperty(value = "模板id",example = "sdfsadfsdfsa0-sdafsafsa-dsa")
    private String token;

}
