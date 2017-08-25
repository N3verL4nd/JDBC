import com.xiya.test.JDBCTools;
import org.apache.commons.dbutils.DbUtils;
import org.junit.Test;

import java.sql.*;

public class ResultSetMetaDataTest {
    @Test
    public void testResultSetMetaData() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM persons";

        try {
            connection = JDBCTools.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println(resultSetMetaData.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(connection, statement, resultSet);
        }
    }
}
