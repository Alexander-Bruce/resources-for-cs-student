<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/15
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <jsp:useBean id="registerInfo" class="backend.experiment3.entity.RegisterInfo" scope="session" />
    <jsp:setProperty name="registerInfo" property="*" />

    <%
        RequestDispatcher dispatcher = request.getRequestDispatcher("D.jsp");
        dispatcher.forward(request, response);
    %>
</body>
</html>
