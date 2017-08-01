import com.xiya.test.JDBCTools;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * Created by N3verL4nd on 2017/4/11.
 */
public class JdbcTest {
    @Test
    public void testProperties() throws IOException {
        Properties properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(in);
        properties.list(System.out);
        in.close();
    }

    @Test
    public void testServiceLoader() {
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(java.sql.Driver.class);
        Iterator<Driver> driversIterator = serviceLoader.iterator();
        try{
            while(driversIterator.hasNext()) {
                Driver driver = driversIterator.next();
                System.out.println(driver.getClass().getName());
//                System.out.println(driver.acceptsURL("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=true"));
            }
        } catch(Throwable t) {
            // Do nothing
        }
    }

    /**
     * Driver是一个数据库厂商必须提供实现的接口，能从其中获得数据库连接。
     */
    @Test
    public void testDriver() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "lgh123");
        info.put("characterEncoding", "UTF-8");
        info.put("useSSL", "true");
        info.put("useUnicode", "true");


        Connection connection = driver.connect(url, info);
        //DriverManager.getConnection(url, info);
        System.out.println(connection);
        connection.close();
    }

    public Connection getConnection1() {
        Properties properties = new Properties();
        InputStream in;
        in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        if (in == null) {
            return null;
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //properties.list(System.out);
        String driverClassName = properties.getProperty("jdbc.driverClassName");
        String jdbcUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        Driver driver = null;
        try {
            driver = (Driver) Class.forName(driverClassName).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        try {
            if (driver != null) {
                connection = driver.connect(jdbcUrl, info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Test
    public void testGetConnection() {
        Connection connection = getConnection1();
        System.out.println(connection);
    }

    public Connection getConnection2() {
        Properties properties = new Properties();
        InputStream in;
        in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        if (in == null) {
            return null;
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driverClassName = properties.getProperty("jdbc.driverClassName");
        String jdbcUrl = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);

        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl, info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Test
    public void testStatement() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        int id = 0;
        try {
            connection = JDBCTools.getConnection();
            String sql = "INSERT INTO persons(name, age, birth, email) VALUES(?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "xiya");
            statement.setInt(2, 25);
            statement.setDate(3, new Date(new java.util.Date().getTime()));
            statement.setString(4, "5342735@qq.com");
            //int result = statement.executeUpdate("INSERT INTO persons(name, age) VALUES('n3verl4nd',25)");
            //int result = statement.executeUpdate("DELETE FROM persons WHERE id = 6");
            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println("插入数据的主键为：" + id);
            }

            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i + 1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    System.out.println("rs closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                    System.out.println("statement closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //删除插入数据
            testDelete(id);
        }
    }

    public void testDelete(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "DELETE FROM persons WHERE id = ?";

        try {
            conn = JDBCTools.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(stmt);
            JDBCTools.closeQuietly(conn);
        }
    }

    @Test
    public void testResultSet() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons";
        try {
            connection = JDBCTools.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3)
                        + " " + rs.getDate(4) + " " + rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(connection, stmt, rs);
        }
    }

    @Test
    public void testDatabaseMetaData() {
        Connection conn = null;
        DatabaseMetaData metaData = null;
        ResultSet rs = null;
        try {
            conn = JDBCTools.getConnection();
            metaData = conn.getMetaData();
            System.out.println("数据库名称：" + metaData.getDatabaseProductName());
            System.out.println("数据库版本号：" + metaData.getDatabaseProductVersion());
            System.out.println("数据库连接URL：" + metaData.getURL());
            System.out.println("数据库用户名：" + metaData.getUserName());
            System.out.println("驱动程序名称：" + metaData.getDriverName());
            System.out.println("数据库版本号：" + metaData.getDriverVersion());


            //返回数据库中有哪些数据库
            rs = metaData.getCatalogs();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(rs);
            JDBCTools.closeQuietly(conn);
        }
    }
}
