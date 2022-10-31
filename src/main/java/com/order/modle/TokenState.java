package com.order.modle;

import lombok.Data;

/**
 * @Author: jiakun
 * @Date: 2021/3/9 18:35
 * @Description
 */
@Data
public class TokenState {

    /**
     * 是否过期 是
     */
    private Boolean expires;

    /**
     * 是否拥有权限
     */
    private Boolean hasAuthority;
}
