package com.xiya.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

/**
 * Created by N3verL4nd on 2017/4/18.
 */
public class JDBCTools {

    /**
     * 获取数据库连接
     * @return Connection
     */
    public static Connection getConnection() {
        Properties properties = new Properties();
        InputStream in;
        in = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driverClassName = properties.getProperty("jdbc.driverClassName");
        String jdbcUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        /*try {
            //因为 ServiceLoader 所以不再需要如下函数调用。
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        Connection connection = null;
        //输出日志
        DriverManager.setLogWriter(new PrintWriter(System.out));
        try {
            connection = DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**********Copied from DBUtil**********/
    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static void closeQuietly(ResultSet rs) {
        try {
            close(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    public static void closeQuietly(Statement stmt) {
        try {
            close(stmt);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    /**
     * 释放数据库资源
     * @param conn  Connection
     * @param stmt  Statement
     * @param rs    ResultSet
     */
    public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
        try {
            closeQuietly(rs);
        } finally {
            try {
                closeQuietly(stmt);
            } finally {
                closeQuietly(conn);
            }
        }
    }
    /**********Copied from DBUtil**********/
}
