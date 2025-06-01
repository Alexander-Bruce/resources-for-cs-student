<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/28
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>信息修改</title>
</head>
<body>
    <form method="POST" action="GetModify" >
        <p align="center">用户信息修改

        <p align="center">
            用户名：<input type="text" name="preUsername" size="20" >

        <p align="center">
            密码：<input type="text" name="Password" size="20" >

        <p align="center">
            年龄：
            <input type="text" name="UserAge" size="20">

        <p align="center">
            性别：
            <input type="radio" name="gender" value=male  CHECKED> 男
            <input type="radio" name="gender" value=female> 女</p>

        <p align="center">
            请上传照片：
            <!-- file元素不允许有默认值 -->
            <INPUT TYPE="file" NAME="uploadfile" ></p>

        <p align="center">
            <input type="submit" value=" 提交">
            <input type="reset" value="重写"></p>

    </form>
</body>
</html>
