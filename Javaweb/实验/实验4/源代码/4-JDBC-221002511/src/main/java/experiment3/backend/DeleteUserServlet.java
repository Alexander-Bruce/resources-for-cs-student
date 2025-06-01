package experiment3.backend;

import experiment3.backend.beans.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM user WHERE username = ?"; // 删除用户表中的用户
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeUpdate();

            response.sendRedirect("admin.jsp"); // 删除后重定向到管理员仪表板
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确保资源被释放
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }
}

