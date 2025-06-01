package backend.database_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseAverage {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DruidDBConnection.getConnection();

            // 遍历课程表
            String courseSql = "SELECT * FROM course";
            ps = conn.prepareStatement(courseSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String courseId = rs.getString("cid");
                String courseName = rs.getString("name");

                // 计算男同学作业的平均成绩
                String averageScoreSql = "SELECT AVG(h.grade1) AS h1_avg, AVG(h.grade2) AS h2_avg, AVG(h.grade3) AS h3_avg " +
                        "FROM homework h " +
                        "JOIN student s ON h.sid = s.sid " +
                        "WHERE s.gender = N'男' AND h.cid = ?";
                try (PreparedStatement avgPs = conn.prepareStatement(averageScoreSql)) {
                    avgPs.setString(1, courseId);
                    try (ResultSet avgRs = avgPs.executeQuery()) {
                        if (avgRs.next()) {
                            System.out.println("课程: " + courseName);
                            System.out.println("作业1平均成绩: " + avgRs.getDouble("h1_avg"));
                            System.out.println("作业2平均成绩: " + avgRs.getDouble("h2_avg"));
                            System.out.println("作业3平均成绩: " + avgRs.getDouble("h3_avg"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

