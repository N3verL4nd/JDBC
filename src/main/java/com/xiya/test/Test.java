package com.xiya.test;

import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by N3verL4nd on 2017/4/17.
 */
public class Test {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //遍历加载的驱动程序
        /*System.out.println("-----------------------------------------------");
        Enumeration<Driver> enumeration = DriverManager.getDrivers();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
        System.out.println("-----------------------------------------------");*/

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //设置将数据输出到控制台
            DriverManager.setLogWriter(new PrintWriter(System.out));
            conn = DriverManager.getConnection("jdbc:mysql:///test?useUnicode=true&characterEncoding=UTF-8&useSSL=true", "root", "lgh123");
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
                } catch (SQLException e) {
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
