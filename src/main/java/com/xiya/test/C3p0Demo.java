package com.xiya.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
* @file C3p0Demo.java
* @CopyRight (C) http://blog.csdn.net/x_iya
* @Description 使用C3P0数据库连接池
* @author N3verL4nd
* @email lgh1992314@qq.com
* @date 2017/7/31
*/
public class C3p0Demo {
    public static void main(String[] args) {
        DataSource dataSource = new ComboPooledDataSource("c3p0.properties");
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
