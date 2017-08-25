import com.xiya.entity.Person;
import com.xiya.test.JDBCTools;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryLoader;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class DBUtilsTest {

    private QueryRunner queryRunner;

    @Before
    public void setUp() {
        queryRunner = new QueryRunner();
    }

    @Test
    public void testQueryLoader() {

        //匹配以xml结尾的文件
        Pattern dotXml = Pattern.compile(".+\\.[xX][mM][lL]");
        String path = "E:\\t00ls\\Ditto\\Language\\Danish.xml";
        System.out.println(dotXml.matcher(path).matches());

        try {
            Map<String, String> map = QueryLoader.instance().load("/sql.properties");
            System.out.println(map.get("JDBC.QUERY_STRING"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试 QueryRunner 类的 update 方法
     */
    @Test
    public void testQueryRunnerUpdate() {
        Connection connection = null;

        String sql = "DELETE FROM persons WHERE id in (?, ?)";

        try {
            connection = JDBCTools.getConnection();
            //返回受影响的行数
            int rows = queryRunner.update(connection, sql, 41, 39);
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 测试 QueryRunner 类的 insert 方法
     */
    @Test
    public void testQueryRunnerInsert() {
        Connection connection = null;
        String sql = "INSERT INTO persons(name, age, birth, email) VALUES(?, ?, ?, ?)";
        try {
            connection = JDBCTools.getConnection();
            Object[] args= {"测试", 99, new java.sql.Date(new Date().getTime()), "tmd@qq.com"};
            //返回插入数据的主键
            Map<String, Object> map = queryRunner.insert(connection, sql, new MapHandler(), args);
            System.out.println(map);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //自定义 ResultSetHandler
    @Test
    public void testQueryRunnerQuery() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "SELECT * FROM persons";
            List<Person> list = queryRunner.query(connection, sql, rs -> {
                List<Person> list1 = new ArrayList<>();
                while (rs.next()) {
                    Person person = new Person(rs.getInt(1), rs.getString(2),
                            rs.getInt(3), rs.getDate(4), rs.getString(5));
                    list1.add(person);
                }
                return list1;
            });
            list.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testBeanHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "SELECT * FROM persons WHERE id = ?";
            Person person = queryRunner.query(connection, sql, new BeanHandler<>(Person.class), 26);
            System.out.println(person);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testBeanListHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "SELECT * FROM persons";
            List<Person> list = queryRunner.query(connection, sql, new BeanListHandler<>(Person.class));
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testScalarHandler() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM persons";
        try {
            connection = JDBCTools.getConnection();
            Long query = queryRunner.query(connection, sql, new ScalarHandler<Long>());
            System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testMapHandler() {
        Connection connection = null;
        String sql = "SELECT * FROM persons WHERE id = ?";
        try {
            connection = JDBCTools.getConnection();
            Map<String, Object> map = queryRunner.query(connection, sql, new MapHandler(), 26);
            System.out.println(map.get("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testArrayHandler() {
        Connection connection = null;
        String sql = "SELECT * FROM persons WHERE id = ?";
        try {
            connection = JDBCTools.getConnection();
            Object[] obj = queryRunner.query(connection, sql, new ArrayHandler(), 26);
            System.out.println(Arrays.toString(obj));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testColumnListHandler() {
        Connection connection = null;
        String sql = "SELECT name FROM persons";
        try {
            connection = JDBCTools.getConnection();
            List<String> list = queryRunner.query(connection, sql, new ColumnListHandler<String>());
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testArrayListHandler() {
        Connection connection = null;
        String sql = "SELECT * FROM persons";
        try {
            connection = JDBCTools.getConnection();
            List<Object[]> list = queryRunner.query(connection, sql, new ArrayListHandler());
            list.forEach(objects -> System.out.println(Arrays.toString(objects)));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testMapListHandler() {
        Connection connection = null;
        String sql = "SELECT * FROM persons";
        try {
            connection = JDBCTools.getConnection();
            List<Map<String, Object>> list = queryRunner.query(connection, sql, new MapListHandler());
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testBeanMapHandler() {
        Connection connection = null;
        String sql = "SELECT * FROM persons";
        try {
            connection = JDBCTools.getConnection();
            Map<String, Person> map = queryRunner.query(connection, sql, new BeanMapHandler<String, Person>(Person.class, "name"));
            for (Map.Entry<String, Person> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testKeyedHandler() {
        Connection connection = null;
        String sql = "SELECT * FROM persons";
        try {
            connection = JDBCTools.getConnection();
            Map<Integer, Map<String, Object>> map = queryRunner.query(connection, sql, new KeyedHandler<Integer>("id"));
            for (Map.Entry<Integer, Map<String, Object>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
