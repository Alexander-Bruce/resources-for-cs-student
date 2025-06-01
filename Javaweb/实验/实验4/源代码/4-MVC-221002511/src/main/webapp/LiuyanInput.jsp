<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>留言输入</title>
</head>
<body>
<h2>用户留言</h2>
<form action="submit" method="post">
    <label for="username">用户名:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="content">留言内容:</label><br>
    <textarea id="content" name="content" rows="4" cols="50" required></textarea><br><br>

    <input type="submit" value="提交留言">
</form>

</body>
</html>
