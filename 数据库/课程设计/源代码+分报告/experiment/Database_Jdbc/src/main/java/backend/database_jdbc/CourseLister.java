package backend.database_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseLister {

    public static void listCourses() {
        try (Connection conn = DruidDBConnection.getConnection()) {
            String courseQuery = "SELECT cid, name FROM course";
            try (PreparedStatement coursePs = conn.prepareStatement(courseQuery);
                 ResultSet courseRs = coursePs.executeQuery()) {

                System.out.println("课程表：");
                while (courseRs.next()) {
                    String cid = courseRs.getString("cid");
                    String courseName = courseRs.getString("name");
                    System.out.println("课程ID: " + cid + ", 课程名称: " + courseName);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        listCourses();
    }
}

