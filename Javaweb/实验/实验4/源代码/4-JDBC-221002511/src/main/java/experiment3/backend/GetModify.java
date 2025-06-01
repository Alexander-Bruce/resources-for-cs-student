package experiment3.backend;

import experiment3.backend.beans.DBConnection;
import experiment3.backend.beans.DetailedInfoBean;
import experiment3.backend.beans.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/GetModify")
public class GetModify extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String preUsername = request.getParameter("preUsername");
        String username = request.getParameter("username");
        String pwd = request.getParameter("Password");
        Integer age = Integer.parseInt(request.getParameter("UserAge"));
        String gender = request.getParameter("gender");
        File file = new File(request.getParameter("uploadfile"));
        String pictureLocation = file.getPath();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnection();

            // 查询用户信息
            String sql = "SELECT * FROM user WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, preUsername);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setSexy(resultSet.getString("sexy"));
                user.setPictureLocation(resultSet.getString("picturelocation"));
            } else {
                System.out.println("No user found with username: " + preUsername);
            }

            if (user != null) {
                if (username == null || username.isEmpty()) username = user.getUsername();
                if (pwd == null || pwd.isEmpty()) pwd = user.getPassword();
                if (age <= 0) age = user.getAge();
                if (gender == null || gender.isEmpty()) gender = user.getSexy();
                if (pictureLocation == null || pictureLocation.isEmpty()) pictureLocation = user.getPictureLocation();

                if (pwd.isEmpty()) pwd = "123456";
                if (age <= 0) age = 18;
                if (gender.isEmpty()) gender = "Unknown";
                if (pictureLocation.isEmpty()) pictureLocation = "/";

                String sql1 = "UPDATE user SET username = ?, password = ?, age = ?, sexy = ?, picturelocation = ? WHERE username = ?";
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, pwd);
                preparedStatement.setInt(3, age);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, pictureLocation);
                preparedStatement.setString(6, preUsername);
                preparedStatement.executeUpdate();


                DetailedInfoBean detailedInfoBean = new DetailedInfoBean();
                detailedInfoBean.setUsername(username);
                detailedInfoBean.setAge(age);
                detailedInfoBean.setSexy(gender);
                detailedInfoBean.setPictureLocation(file.getPath());

                request.setAttribute("readdetailedinfobean", detailedInfoBean);
            } else {
                System.out.println("Update not performed because user is null.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("showmodifyinfo.jsp");
        dispatcher.forward(request, response);
    }
}

