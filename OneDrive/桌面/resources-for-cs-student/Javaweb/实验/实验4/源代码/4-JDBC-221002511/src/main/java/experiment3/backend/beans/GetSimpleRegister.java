package experiment3.backend.beans;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/GetSimpleRegister")
public clasGetSimpleRegister extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("User");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        User user = null;

        System.out.println(username);

        if(username == null || username.isEmpty()){
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("simpleregister.jsp");
            dispatcher.forward(request, response);
        }

        try{
            connection = DBConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setSexy(resultSet.getString("sexy"));
                user.setPictureLocation(resultSet.getString("picturelocation"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = null;

        if(user == null){
            dispatcher = request.getRequestDispatcher("detailedregister.jsp");
        } else {
            dispatcher = request.getRequestDispatcher("simpleregister.jsp");
        }

        dispatcher.forward(request, response);

    }

}
