<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录页</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #6a11cb, #2575fc);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        .login-container {
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
            text-align: center;
            width: 350px;
        }

        .login-container h1 {
            color: #333333;
            margin-bottom: 15px;
        }

        .login-container img {
            width: 80px;
            margin-bottom: 15px;
        }

        .login-container p {
            margin: 10px 0;
        }

        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 90%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }

        .login-container input[type="submit"],
        .login-container input[type="reset"] {
            width: 45%;
            padding: 10px;
            margin: 10px 5px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .login-container input[type="submit"]:hover,
        .login-container input[type="reset"]:hover {
            background-color: #0056b3;
        }

        .login-container label {
            display: flex;
            justify-content: flex-start;
            align-items: center;
            font-size: 14px;
        }

        .login-container .checkbox-container {
            margin-top: -5px;
            text-align: left;
            font-size: 14px;
        }

        .login-container .forgot-password {
            text-align: right;
            font-size: 14px;
            margin-top: 5px;
        }

        .login-container a {
            color: #007bff;
            text-decoration: none;
            font-size: 14px;
        }

        .login-container a:hover {
            text-decoration: underline;
        }

        .site-description {
            font-size: 14px;
            color: #777;
            margin-bottom: 20px;
        }

        .login-container .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>欢迎登录</h1>
    <p class="site-description">欢迎来到我们的网站，请登录以继续。</p>
    <form method="post" action="${pageContext.request.contextPath}/login/verify">
        <p>
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" placeholder="请输入用户名">
        </p>
        <p>
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" placeholder="请输入密码">
        </p>
        <div class="checkbox-container">
            <label>
                <input type="checkbox" name="rememberMe"> 记住我
            </label>
        </div>
        <div class="forgot-password">
            <a href="${pageContext.request.contextPath}/forgot-password">忘记密码？</a>
        </div>
        <p>
            <input type="submit" value="登录">
            <input type="reset" value="重置">
        </p>
        <p>
            <a href="${pageContext.request.contextPath}/register">注册新账号</a>
        </p>
    </form>

    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
        <c:set var="error" value="${null}" scope="request"/>
    </c:if>
</div>
</body>
</html>