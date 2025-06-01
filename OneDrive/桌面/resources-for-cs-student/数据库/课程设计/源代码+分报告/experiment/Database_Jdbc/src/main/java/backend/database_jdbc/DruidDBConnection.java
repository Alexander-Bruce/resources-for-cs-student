package backend.database_jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DruidDBConnection {
    private static DruidDataSource dataSource = new DruidDataSource();

    static {
        dataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=education");
        dataSource.setUsername("SA");
        dataSource.setPassword("Heqinglin2021@");
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        try {
            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

