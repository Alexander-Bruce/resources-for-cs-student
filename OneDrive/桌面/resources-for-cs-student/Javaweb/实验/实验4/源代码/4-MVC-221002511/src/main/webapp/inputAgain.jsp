<%@ page import="backend.domain.LiuyanBean" %><%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/24
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>输入错误</title>
</head>
<body>

<h2>请输入有效的用户名和留言内容</h2>
<form action="submit" method="post">
    <label for="username">用户名:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="content">留言内容:</label><br>
    <textarea id="content" name="content" rows="4" cols="50" required></textarea><br><br>

    <input type="submit" value="重新提交">
</form>
</body>
</html>

