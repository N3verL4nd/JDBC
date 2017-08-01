package com.xiya.test;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* @file DbcpDemo.java
* @CopyRight (C) http://blog.csdn.net/x_iya
* @Description 使用DBCP数据库连接池
* @author N3verL4nd
* @email lgh1992314@qq.com
* @date 2017/7/31
*/
public class DbcpDemo {
    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream in = DbcpDemo.class.getClassLoader().getResourceAsStream("dbcp.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataSource dataSource = null;
        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
