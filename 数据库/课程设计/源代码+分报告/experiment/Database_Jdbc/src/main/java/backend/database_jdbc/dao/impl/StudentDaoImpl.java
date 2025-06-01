package backend.database_jdbc.dao.impl;

import backend.database_jdbc.dao.StudentDao;
import backend.database_jdbc.DruidDBConnection;
import backend.database_jdbc.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public void addStudent(Student student) {
        String sql = "INSERT INTO student (sid, name, gender, major_class, birth, phone) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getSid());
            ps.setString(2, student.getName());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getMajorClass());
            ps.setDate(5, new java.sql.Date(student.getBirth().getTime()));
            ps.setString(6, student.getPhone());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, gender = ?, major_class = ?, birth = ?, phone = ? WHERE sid = ?";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getGender());
            ps.setString(3, student.getMajorClass());
            ps.setDate(4, new java.sql.Date(student.getBirth().getTime()));
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getSid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(String sid) {
        String sql = "DELETE FROM student WHERE sid = ?";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudent(String sid) {
        String sql = "SELECT * FROM student WHERE sid = ?";
        Student student = null;
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    student = new Student();
                    student.setSid(rs.getString("sid"));
                    student.setName(rs.getString("name"));
                    student.setGender(rs.getString("gender"));
                    student.setMajorClass(rs.getString("major_class"));
                    student.setBirth(rs.getDate("birth"));
                    student.setPhone(rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student student = new Student();
                student.setSid(rs.getString("sid"));
                student.setName(rs.getString("name"));
                student.setGender(rs.getString("gender"));
                student.setMajorClass(rs.getString("major_class"));
                student.setBirth(rs.getDate("birth"));
                student.setPhone(rs.getString("phone"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

