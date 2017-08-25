package com.xiya.test;

import java.sql.*;

/**
* @file Test.java
* @CopyRight (C) http://blog.csdn.net/x_iya
* @Description JDBC实例
* @author N3verL4nd
* @email lgh1992314@qq.com
* @date 2017/7/29
*/
public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
        String username= "root";
        String password = "lgh123";

        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM persons");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch ( SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
