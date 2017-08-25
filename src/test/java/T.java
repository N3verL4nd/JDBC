import com.xiya.entity.Person;
import com.xiya.test.JDBCTools;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.RowProcessor;
import org.junit.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class T {

    @Test
    public void test() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            rs = statement.executeQuery(sql);
            while(rs.next()) {
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    System.out.println(resultSetMetaData.getColumnLabel(i) + ":" + rs.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testQuery() {
        QueryRunner queryRunner = new QueryRunner();
        Connection connection = null;
        String sql;
        try {
            connection = JDBCTools.getConnection();


            //toBean
            System.out.println("toBean");
            sql = "SELECT * FROM persons WHERE id = ?";
            Person person = queryRunner.query(connection, sql, rs ->
            {
                RowProcessor rowProcessor = new BasicRowProcessor();
                if (!rs.next()) {
                    return null;
                }
                return rowProcessor.toBean(rs, Person.class);
            }, 26);
            System.out.println(person);

            //toBeanList
            System.out.println("toBeanList");
            sql = "SELECT * FROM persons";
            List<Person> list = queryRunner.query(connection, sql, rs -> {
                RowProcessor rowProcessor = new BasicRowProcessor();
                return rowProcessor.toBeanList(rs, Person.class);
            });
            list.forEach(System.out::println);

            //toArray
            System.out.println("toArray");
            sql = "SELECT * FROM persons WHERE id = ?";
            Object[] objects = queryRunner.query(connection, sql, rs -> {
                if (!rs.next()) {
                    return null;
                }
                RowProcessor rowProcessor = new BasicRowProcessor();
                return rowProcessor.toArray(rs);
            }, 26);
            System.out.println(Arrays.toString(objects));

            //toMap
            System.out.println("toMap");
            sql = "SELECT id, name AS personName, age, birth, email FROM persons WHERE id = ?";
            Map<String, Object> map = queryRunner.query(connection, sql, rs -> {
                if (!rs.next()) {
                    return null;
                }
                RowProcessor rowProcessor = new BasicRowProcessor();
                return rowProcessor.toMap(rs);
            }, 26);
            System.out.println(map);
            //别名
            System.out.println(map.get("personName"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
