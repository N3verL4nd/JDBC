package com.xiya.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by N3verL4nd on 2017/4/27.
 */
public class C3p0Demo {
    public static void main(String[] args) {
        Properties properties = new Properties();
        InputStream in = C3p0Demo.class.getClassLoader().getResourceAsStream("c3p0.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driverClassName = properties.getProperty("jdbc.driverClassName");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Connection conn = null;
        try {
            dataSource.setDriverClass(driverClassName);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            dataSource.setAcquireIncrement(3);
            dataSource.setInitialPoolSize(10);
            dataSource.setMinPoolSize(2);
            dataSource.setMaxPoolSize(10);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        try {
            conn = dataSource.getConnection();
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        dataSource.close();
    }
}
