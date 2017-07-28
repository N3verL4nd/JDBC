import com.xiya.entity.Person;
import com.xiya.test.JDBCTools;
import org.junit.Test;
import java.sql.*;
import java.time.LocalDate;

/**
 * Created by N3verL4nd on 2017/4/27.
 */
public class T {
    @Test
    public void TestQuery() {
        Connection conn = JDBCTools.getConnection();
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM persons";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " +
                        rs.getString(2) + " " +
                        rs.getInt(3) + " " +
                        rs.getDate(4) + " " +
                        rs.getString(5)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(conn, stmt, rs);
        }
    }

    @Test
    public void TestUpdate() {
        Connection conn = JDBCTools.getConnection();
        Statement stmt = null;
        LocalDate birth = LocalDate.of(1992, 3, 14);

        Person person = new Person("张天一", 25, birth, "lgh1992314@qq.com");
        String sql = "INSERT INTO persons(name,age, birth, email) " + "VALUES('" +
                person.getName() + "'," +
                person.getAge() + ",'" +
                person.getBirth().toString() +
                "','" + person.getEmail() + "')";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(stmt);
            JDBCTools.closeQuietly(conn);
        }
    }

    @Test
    public void TestPreparedStatement() {
        Connection conn = JDBCTools.getConnection();
        PreparedStatement pstmt = null;
        String sql = "UPDATE persons SET name = ? WHERE id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "芊漪");
            pstmt.setInt(2, 9);
            int flag = pstmt.executeUpdate();
            System.out.println(flag);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.closeQuietly(pstmt);
            JDBCTools.closeQuietly(conn);
        }
    }
}
