<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/25
  Time: 9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>完全注册</title>
</head>
<body>

    <form method="POST" action="GetDetailedRegister" >
        <p align="center">用户注册

        <<p align="center">
            用户名：<input type="text" name="User" value="${param.User}" readonly size="20">
            <input type="hidden" name="User" value="${param.User}">

        <p align="center">
            密码：<input type="text" name="Password" size="20" >

        <p align="center">
            年龄：
            <input type="text" name="age" >

        <p align="center">
            性别：
            <input type="radio" name="sexy" value=male  CHECKED> 男
            <input type="radio" name="sexy" value=female> 女</p>

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
