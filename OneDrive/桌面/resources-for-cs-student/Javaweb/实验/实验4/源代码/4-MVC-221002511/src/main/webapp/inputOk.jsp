<%@ page import="backend.domain.LiuyanBean" %><%--
  Created by IntelliJ IDEA.
  User: 26259
  Date: 2024/10/24
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>留言成功</title>
</head>
<body>
<h2>留言提交成功!</h2>
<p>感谢您的留言!</p>

<%
    LiuyanBean liuyanBean = (LiuyanBean) session.getAttribute("message");
    if (liuyanBean != null) {
%>
<p>您的留言内容: <%= liuyanBean.getContent() %></p>
<p>用户名: <%= liuyanBean.getUsername() %></p>
<%
} else {
%>
<p>未找到留言信息。</p>
<%
    }
%>

<a href="LiuyanInput.jsp">返回留言页面</a>
</body>
</html>

