import com.xiya.entity.Person;
import com.xiya.test.DaoImpl;
import com.xiya.test.JDBCTools;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DaoImplTest {
    private DaoImpl dao;

    @Before
    public void setUp() {
        dao = new DaoImpl();
    }

    @Test
    public void testGet() {
        Connection connection = null;
        String sql = "SELECT * FROM persons WHERE id = ?";
        try {
            connection = JDBCTools.getConnection();
            Person person = dao.get(connection, sql, 26);
            System.out.println(person);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testGetList() {
        Connection connection = null;
        String sql = "SELECT * FROM persons";
        try {
            connection = JDBCTools.getConnection();
            List<Person> list = dao.getList(connection, sql, new Object[0]);
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    @Test
    public void testGetForValue() {
        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM persons";
        try {
            connection = JDBCTools.getConnection();
            Long rows = dao.getForValue(connection, sql, new Object[0]);
            System.out.println(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }
}
