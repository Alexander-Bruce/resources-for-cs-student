<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎</title>
    <style>
        body {
            font-family: "Helvetica Neue", Arial, sans-serif;
            background-color: #e0f7fa;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #00796b;
            color: white;
            padding: 20px 0;
            text-align: center;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }
        h1 {
            font-size: 32px;
            margin-bottom: 20px;
        }
        h2 {
            font-size: 24px;
            color: #00796b;
            margin-top: 30px;
            margin-bottom: 10px;
        }
        p {
            font-size: 16px;
            color: #4f4f4f;
            line-height: 1.6;
            margin-bottom: 20px;
        }
        ul {
            list-style-type: disc;
            margin-left: 20px;
        }
        a {
            display: inline-block;
            padding: 12px 20px;
            font-size: 16px;
            color: white;
            background-color: #00796b;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }
        a:hover {
            background-color: #004d40;
        }
        footer {
            text-align: center;
            margin-top: 40px;
            padding: 20px 0;
            background-color: #f5f5f5;
            border-top: 1px solid #ccc;
        }
    </style>
</head>
<body>

<header>
    <h1>欢迎来到我的平台！</h1>
</header>

<div class="container">
    <h2>关于网站</h2>
    <p>这是一个关于cookie实现和实践的平台</p>

    <h2>网站功能</h2>
    <p>以下是您可以在我们的网站上找到的一些功能：</p>
    <ul>
        <li>用户注册和登录</li>
        <li>查看登录登出时间</li>
    </ul>

    <h2>立即开始</h2>
    <p>请点击下方按钮，注册或登录吧</p>
    <a href="${pageContext.request.contextPath}/user/login">登录或注册</a>
</div>

<footer>
    <p>&copy; 2024 我的网站。保留所有权利。</p>
    <p>联系方式: support@example.com</p>
</footer>

</body>
</html>
