package com.order.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * @Author: wanqian
 * @Date: 2020/10/21 14:46
 */
public class UpperTableStrategy extends SpringPhysicalNamingStrategy {

    /**
     * 将表名全部转换成大写
     * @param name
     * @param context
     * @return
     */
    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        String tableName = name.getText().toUpperCase();
        return Identifier.toIdentifier(tableName);
    }


    /**
     * 将字段名转为大写-下划线隔开. 默认小写-下划线
     * @param name
     * @param jdbcEnvironment
     * @return
     */
    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier lowerName = super.toPhysicalSchemaName(name, jdbcEnvironment);
        String columnName = lowerName.getText().toUpperCase();
        return Identifier.toIdentifier(columnName);
    }
}
