package com.order.modle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: jiakun
 * @Date: 2020/10/22 14:45
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBO implements Serializable {

    private Integer id;

    private String userName;

    private String status;

    private String roleCode;

}
