package backend.database_jdbc;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet(name = "insertStudents", value = "/insert-students")
public class InsertStudents extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        insert();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("dashboard.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 获取数据库连接
            conn = DBConnection.getConnection();
            // 插入两条记录
            String insertSQL = "INSERT INTO student (sid, name, gender, major_class, birth, phone) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSQL);

            // 插入第一条记录
            ps.setString(1, "0601");
            ps.setString(2, "王小明");
            ps.setString(3, "男");
            ps.setString(4, "计算机01");
            ps.setDate(5, Date.valueOf("1999-05-15"));
            ps.setString(6, "12345678901");
            ps.executeUpdate();

            // 插入第二条记录
            ps.setString(1, "0602");
            ps.setString(2, "李小红");
            ps.setString(3, "女");
            ps.setString(4, "生物01");
            ps.setDate(5, Date.valueOf("1998-08-20"));
            ps.setString(6, "10987654321");
            ps.executeUpdate();

            // 查询并显示所有记录
            String selectSQL = "SELECT * FROM student";
            ps = conn.prepareStatement(selectSQL);
            rs = ps.executeQuery();

            System.out.println("学号\t姓名\t性别\t专业班级\t出生日期\t电话号码");
            while (rs.next()) {
                String sid = rs.getString("sid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String major_class = rs.getString("major_class");
                Date birth = rs.getDate("birth");
                String phone = rs.getString("phone");

                System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n", sid, name, gender, major_class, birth, phone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            try {
                DBConnection.dbClose(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
