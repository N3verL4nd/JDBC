package com.xiya.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by N3verL4nd on 2017/4/27.
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
        BasicDataSource dataSource = null;
        try {
            dataSource = BasicDataSourceFactory.createDataSource(properties);
            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
