package com.order.modle.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jiakun
 * @Date: 2021/3/9 23:03
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCheckParam {

    private String token;

    private String requestURI;

    private String method;
}
