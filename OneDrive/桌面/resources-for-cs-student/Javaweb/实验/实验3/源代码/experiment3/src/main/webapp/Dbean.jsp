<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/15
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册信息</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .info-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            margin: auto;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .info {
            margin: 8px 0;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
    <jsp:useBean id="registerInfo" class="backend.experiment3.entity.RegisterInfo" scope="session"/>
    <%
        if(registerInfo.getPassword() == null || registerInfo.getUsername() == null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
            dispatcher.forward(request, response);
            return;
        }

        String username = registerInfo.getUsername();
        String password = registerInfo.getPassword();
        LocalDateTime loginTime = (LocalDateTime) session.getAttribute("login");
        Integer count = (Integer) application.getAttribute("count");
    %>

<div class="info-container">
    <h2>注册信息</h2>
    <div class="info">
        <strong>用户名:</strong> <%= username %>
    </div>
    <div class="info">
        <strong>密码:</strong> ••••••  <!-- 打码显示 -->
    </div>
    <div class="info">
        <strong>登录时间:</strong> <%= loginTime%>
    </div>
    <div class="info">
        <strong>浏览次数:</strong> <%= count %>
    </div>
</div>

</body>
</html>
