<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册页</title>
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

        .register-container {
            background-color: #ffffff;
            padding: 20px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 360px;
        }

        .register-container h1 {
            color: #333333;
            margin-bottom: 20px;
        }

        .register-container form p {
            margin: 10px 0;
        }

        .register-container input[type="text"],
        .register-container input[type="password"] {
            width: 90%;
            padding: 8px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .register-container input[type="submit"],
        .register-container input[type="reset"] {
            width: 45%;
            padding: 8px;
            margin: 10px 5px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }

        .register-container input[type="submit"]:hover,
        .register-container input[type="reset"]:hover {
            background-color: #0056b3;
        }

        .register-container .footer {
            margin-top: 15px;
            font-size: 14px;
        }

        .register-container .footer a {
            color: #007bff;
            text-decoration: none;
        }

        .register-container .footer a:hover {
            text-decoration: underline;
        }

        .password-hint {
            font-size: 12px;
            color: #666;
            text-align: left;
            margin: -10px 0 10px 10%;
        }

        .error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h1>用户注册</h1>
    <form method="post" action="${pageContext.request.contextPath}/register/verify">
        <p>
            <label for="username">用户名：</label><br>
            <input type="text" id="username" name="username" placeholder="请输入用户名">
        </p>
        <p>
            <label for="pwd1">密码：</label><br>
            <input type="password" id="pwd1" name="pwd1" placeholder="请输入密码">
        <div class="password-hint">密码需至少包含8个字符，包含字母和数字。</div>
        </p>
        <p>
            <label for="pwd2">确认密码：</label><br>
            <input type="password" id="pwd2" name="pwd2" placeholder="请再次输入密码">
        </p>
        <p>
            <input type="submit" value="注册">
            <input type="reset" value="重置">
        </p>
    </form>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
        <c:set var="error" value="${null}" scope="request"/>
    </c:if>

    <div class="footer">
        已有账号？<a href="${pageContext.request.contextPath}/login">返回登录</a>
    </div>
</div>
</body>
</html>