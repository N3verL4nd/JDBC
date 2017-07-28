package com.xiya.test;

import com.xiya.entity.Person;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by N3verL4nd on 2017/4/26.
 */
public class DbUtilsTest {
    private static String driverClassName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql:///test?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
    private static String user = "root";
    private static String password = "lgh123";

    public static void main(String[] args) throws SQLException {
        Connection conn = JDBCTools.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        List<Person> list = queryRunner.query(conn, "SELECT name, age FROM persons", new BeanListHandler<Person>(Person.class));
        for (Person person : list) {
            System.out.println(person);
        }
        DbUtils.closeQuietly(conn);
    }
}
