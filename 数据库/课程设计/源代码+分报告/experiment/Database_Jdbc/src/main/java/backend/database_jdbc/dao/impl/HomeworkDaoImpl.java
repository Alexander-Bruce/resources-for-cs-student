package backend.database_jdbc.dao.impl;

import backend.database_jdbc.DruidDBConnection;
import backend.database_jdbc.dao.HomeworkDao;
import backend.database_jdbc.entity.Homework;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeworkDaoImpl implements HomeworkDao {

    @Override
    public void addHomework(Homework homework) throws SQLException {
        String sql = "INSERT INTO homework (cid, sid, grade1, grade2, grade3) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, homework.getCid());
            ps.setString(2, homework.getSid());
            ps.setBigDecimal(3, toNumeric(homework.getGrade1()));
            ps.setBigDecimal(4, toNumeric(homework.getGrade2()));
            ps.setBigDecimal(5, toNumeric(homework.getGrade3()));
            ps.executeUpdate();
        }
    }

    @Override
    public void updateHomework(Homework homework) throws SQLException {
        String sql = "UPDATE homework SET cid = ?, sid = ?, grade1 = ?, grade2 = ?, grade3 = ? WHERE cid = ? and sid = ?";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, homework.getCid());
            ps.setString(2, homework.getSid());
            ps.setDouble(3, homework.getGrade1());
            ps.setDouble(4, homework.getGrade2());
            ps.setDouble(5, homework.getGrade3());
            ps.setString(6, homework.getCid());
            ps.setString(7, homework.getSid());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteHomework(String cid, String sid) throws SQLException {
        String sql = "DELETE FROM homework WHERE cid = ? and sid = ?";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cid);
            ps.setString(2, sid);
            ps.executeUpdate();
        }
    }

    @Override
    public Homework getHomeworkById(String cid, String sid) throws SQLException {
        Homework homework = null;
        String sql = "SELECT * FROM homework WHERE cid = ? and sid = ?";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cid);
            ps.setString(2, sid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    homework = new Homework(
                            rs.getString("cid"),
                            rs.getString("sid"),
                            rs.getDouble("grade1"),
                            rs.getDouble("grade2"),
                            rs.getDouble("grade3")
                    );
                }
            }
        }
        return homework;
    }

    @Override
    public List<Homework> getAllHomeworks() {
        List<Homework> homeworks = new ArrayList<>();
        String sql = "SELECT * FROM homework";
        try (Connection conn = DruidDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Homework homework = new Homework(
                        rs.getString("cid"),
                        rs.getString("sid"),
                        rs.getDouble("grade1"),
                        rs.getDouble("grade2"),
                        rs.getDouble("grade3")
                );
                homeworks.add(homework);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return homeworks;
    }

    private BigDecimal toNumeric(double value) {
        // 确保范围在 -99.99 到 99.99 之间
        if (value < 0 || value > 200.00) {
            throw new IllegalArgumentException("Value out of range for numeric(4,2): " + value);
        }
        // 转换为 BigDecimal，并设置小数位
        return BigDecimal.valueOf(value).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}

