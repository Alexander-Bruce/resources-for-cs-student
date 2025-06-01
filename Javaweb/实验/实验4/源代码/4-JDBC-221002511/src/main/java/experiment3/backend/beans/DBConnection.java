package experiment3.backend.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {

    public static Connection getConnection() throws Exception{
        Connection conn = null;

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jdbc_experiment";
        conn = DriverManager.getConnection(url,"root","123456");

        return conn;
    }

    //释放资源
    public static void dbClose(Connection conn,PreparedStatement ps,ResultSet rs)
            throws SQLException
    {
        rs.close();
        ps.close();
        conn.close();
    }


}
