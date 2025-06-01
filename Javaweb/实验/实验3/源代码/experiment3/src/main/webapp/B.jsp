<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/15
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Result</title>
</head>
<body>
<h2>Registration Successful!</h2>

    <%
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        session.setAttribute("username", username);
        session.setAttribute("password", password);

        RequestDispatcher dispatcher = request.getRequestDispatcher("Bbean.jsp");
        dispatcher.forward(request, response);
    %>
</body>
</html>
