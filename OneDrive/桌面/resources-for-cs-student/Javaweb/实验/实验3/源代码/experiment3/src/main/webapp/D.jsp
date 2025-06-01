<%@ page import="java.time.LocalDateTime" %><%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/15
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        Integer count = (Integer) application.getAttribute("count");
        LocalDateTime loginTime= (LocalDateTime) session.getAttribute("login");


        if(count == null) count = 0;

        if(loginTime == null) loginTime = LocalDateTime.now();

        count++;
        loginTime = LocalDateTime.now();

        application.setAttribute("count", count);
        session.setAttribute("login", loginTime);

        RequestDispatcher dispatcher = request.getRequestDispatcher("Dbean.jsp");
        dispatcher.forward(request, response);
    %>

</body>
</html>
