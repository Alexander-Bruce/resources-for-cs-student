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

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int age = Integer.parseInt(request.getParameter("age"));
        String sexy = request.getParameter("sexy");
        String pictureLocation = request.getParameter("pictureLocation");
        String description = request.getParameter("description");
        String createdTime = request.getParameter("createdTime");
        try {
            updateUser(username, password, age, sexy, pictureLocation, description, createdTime);
            response.sendRedirect("admin.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "修改失败");
        }
    }

    private void updateUser(String username, String password, int age, String sexy, String pictureLocation, String description, String createdTime) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE user SET password=?, age=?, sexy=?, picturelocation=?, description=?, created_time=? WHERE username=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, age);
            ps.setString(3, sexy);
            ps.setString(4, pictureLocation);
            ps.setString(5, description);
            ps.setString(6, createdTime);
            ps.setString(7, username);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }
}

