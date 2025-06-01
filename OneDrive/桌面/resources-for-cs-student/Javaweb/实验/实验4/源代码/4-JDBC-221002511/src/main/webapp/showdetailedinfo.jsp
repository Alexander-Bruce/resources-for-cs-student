<%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/25
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>显示详细注册信息</title>
</head>
<body>
  <center>
    <H1><font color="REd">登陆成功，您的详细注册信息如下</font></H1>
    <jsp:useBean id="readdetailedinfobean" type="experiment3.backend.beans.DetailedInfoBean" scope="request" />
    <p>您的用户名是：
      <jsp:getProperty name="readdetailedinfobean" property="username" />
    <p>您的年龄是：
      <jsp:getProperty name="readdetailedinfobean" property="age" />
    <p>您的性别是：
      <jsp:getProperty name="readdetailedinfobean" property="sexy" />
    <p>您的照片存储在服务器上的位置是：
      <jsp:getProperty name="readdetailedinfobean" property="pictureLocation" />
    <p>
      <a href="modify.jsp">修改您的信息</a>
    </p>
  </center>
</body>
</html>
