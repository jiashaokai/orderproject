package com.order.modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Author: wanqian
 * @Date: 2020/10/21 20:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginParam {

    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */

    private String password;

    /**
     * 记住用户七天
     */
    private boolean rememberMe;
}
