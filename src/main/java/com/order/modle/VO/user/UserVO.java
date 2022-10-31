package com.order.modle.VO.user;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: wanqian
 * @Date: 2020/10/21 20:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "用户信息", description = "com.whstone.xfkbmanager.model.VO.user.UserVO")
public class UserVO {

    @ApiModelProperty(value = "id", example = "1")
    @JSONField(name = "id")
    private Integer userId;

    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;

    @ApiModelProperty(value = "登录账号", example = "admin")
    private String loginName;

    @ApiModelProperty(value = "密码", example = "dgasdvczvbcgsd")
    private String password;

    @ApiModelProperty(value = "状态", example = "enable")
    private String status;

    @ApiModelProperty(value = "创建时间", example = "2020-11-12 11:29:27")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
