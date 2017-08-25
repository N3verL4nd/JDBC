import com.xiya.test.JDBCTools;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTest {

    public int update(Connection conn, String sql, Object ...args) {
        PreparedStatement statement = null;
        int result = 0;
        try {
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(statement);
        }
        return result;
    }

    public Object getValue(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            conn = JDBCTools.getConnection();
            System.out.println("默认事务隔离级别：" + conn.getTransactionIsolation());
            /*
            * int TRANSACTION_NONE             = 0;
            * int TRANSACTION_READ_UNCOMMITTED = 1;
            * int TRANSACTION_READ_COMMITTED   = 2;
            * int TRANSACTION_REPEATABLE_READ  = 4;
            * int TRANSACTION_SERIALIZABLE     = 8;
             */
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(conn, statement, rs);
        }
        return null;
    }

    @Test
    public void testTransactionIsolationRead() {
        String sql = "SELECT balance FROM users WHERE id = ?";
        Integer balance = (Integer) getValue(sql, 1);
        System.out.println(balance);
    }

    @Test
    public void testTransactionIsolationUpdate() {
        Connection connection = null;
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try {
            connection = JDBCTools.getConnection();
            connection.setAutoCommit(false);

            update(connection, sql, -500, 1);

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    /**
     * Tom 给 Green 汇款 500 块钱
     * 初始两人各有 1000 块钱
     */
    @Test
    public void testTransaction() {
        Connection connection = null;
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";

        try {
            //获取数据库连接
            connection = JDBCTools.getConnection();

            //开始事务：取消自动提交事务
            connection.setAutoCommit(false);


            //Tom 减少 500 块钱
            update(connection, sql, -500, 1);

            //出现异常操作
            int x = 10 / 0;

            // Green 增加 500 块钱
            update(connection, sql, 500, 2);

            //提交事务
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    //事务回滚
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
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
}
