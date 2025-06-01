<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/25
  Time: 9:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>显示详细注册信息</title>
</head>
<body><center>
    <H1><font color="REd">注册成功，您的详细注册信息如下</font></H1>

    <p>您的用户名是：
            <%=request.getParameter("User") %>

    <p>您的年龄是：
            <%=request.getParameter("age")%>
    <p>您的性别是：
            <%=request.getParameter("sexy") %>
    <p>您的照片存储在服务器上的位置是：
            <%=request.getAttribute("location")%>

</center></body>
</html>
