package com.order.config;

import org.hibernate.dialect.MySQL57Dialect;

/**
 * 自动
 * @Author: wanqian
 * @Date: 2020/10/29 12:07
 */
public class MySQL5DialectUTF8 extends MySQL57Dialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
