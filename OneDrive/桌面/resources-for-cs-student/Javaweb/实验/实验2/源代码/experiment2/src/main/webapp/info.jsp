<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>信息</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #eceff1;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
            width: 360px;
        }
        h1 {
            text-align: center;
            color: #37474f;
            font-size: 24px;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #b0bec5;
        }
        th {
            background-color: #cfd8dc;
            color: #37474f;
        }
        td {
            color: #546e7a;
        }
        tr:last-child td {
            border-bottom: none;
        }
        a {
            display: block;
            text-align: center;
            margin-top: 30px;
            padding: 12px 0;
            color: #ffffff;
            background-color: #546e7a;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s ease;
        }
        a:hover {
            background-color: #37474f;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>用户信息</h1>
    <table>
        <tr>
            <td>姓名：</td>
            <td>${name}</td>
        </tr>
        <tr>
            <td>出生日期：</td>
            <td>${date}</td>
        </tr>
        <tr>
            <td>学校：</td>
            <td>${school}</td>
        </tr>
        <tr>
            <td>描述：</td>
            <td>${description}</td>
        </tr>
        <tr>
            <td>上次登录时间：</td>
            <td>${lastLogin}</td>
        </tr>
        <tr>
            <td>上次登出时间：</td>
            <td>${lastLogOut}</td>
        </tr>
    </table>
    <a href="${pageContext.request.contextPath}/user/logout">登出</a>
</div>

</body>
</html>
