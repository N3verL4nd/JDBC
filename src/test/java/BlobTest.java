import com.xiya.test.JDBCTools;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class BlobTest {

    @Test
    public void testReadBlob() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT pic FROM cars WHERE userid = ?";


        try {
            connection = JDBCTools.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, 3);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob(1);
                System.out.println(blob.length());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection, statement, resultSet);
        }
    }


    /**
     * 插入 BLOB 类型的数据必须使用 PreparedStatement
     * 因为 BLOB 类型的数据是无法使用字符串拼写的
     */
    @Test
    public void testInsertBlob() {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE cars SET pic = ? WHERE userid = ?";
        InputStream in = null;

        try {
            connection = JDBCTools.getConnection();
            statement = connection.prepareStatement(sql);
            in =  BlobTest.class.getClassLoader().getResourceAsStream("benz.jpg");
            statement.setBlob(1, in);
            statement.setInt(2, 2);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
