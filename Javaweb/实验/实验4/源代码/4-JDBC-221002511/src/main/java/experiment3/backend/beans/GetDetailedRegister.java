package experiment3.backend.beans;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetDetailedRegister")
public class GetDetailedRegister extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("User");
        String password = request.getParameter("Password");
        String description = request.getParameter("description");
        int age = Integer.parseInt(request.getParameter("age"));
        String sexy = request.getParameter("sexy");

        String uploadFilePath = request.getParameter("uploadfile");
        File file = new File(uploadFilePath);
        request.setAttribute("location", file.getPath());

        if (insertBeantoDB(username, password, age, sexy, file.getPath(), description)) {
            String forwardUrl = "showregisteredinfo.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
            dispatcher.forward(request, response);
        } else {
            response.getWriter().write("注册失败，请重试。");
        }
    }

    public boolean insertBeantoDB(String username, String password, int age, String sexy, String picturelocation, String description) {
        String sql = "INSERT INTO user (username, password, age, sexy, picturelocation, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, age);
            ps.setString(4, sexy);
            ps.setString(5, picturelocation);
            ps.setString(6, description);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
