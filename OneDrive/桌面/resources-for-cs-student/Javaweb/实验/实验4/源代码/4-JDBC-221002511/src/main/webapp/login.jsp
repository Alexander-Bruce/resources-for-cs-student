<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/25
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>请登录</title>
</head>
<body>

<p align="center">
<H1><font color="black">请登录</font></H1>

<form method="post" action="GetLogin">
    <p align="center">
        用户名：
        <input type="text" name="username">

    <p align="center">
        密码：
        <input type="password" name="pwd">

    <p align="center">
        <input type="submit" value="登录">
        <input type="reset" value="重置"></p>

    <p align="center">
        <a HREF="simpleregister.jsp">注册</a>
</form>

</body>
</html>
