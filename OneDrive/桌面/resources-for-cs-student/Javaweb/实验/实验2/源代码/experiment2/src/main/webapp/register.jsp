<%@ page contentType="text/html;charset=UTF-8" %>
<% session.setAttribute; %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>简历</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #e3f2fd;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            width: 360px;
            text-align: left;
        }
        h1 {
            text-align: center;
            color: #1976d2;
            margin-bottom: 20px;
            font-size: 26px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #424242;
            font-weight: bold;
        }
        input[type="text"], input[type="date"], textarea {
            width: 100%;
            padding: 12px;
            margin: 5px 0 15px;
            border: 1px solid #90caf9;
            border-radius: 6px;
            font-size: 16px;
        }
        textarea {
            resize: vertical;
            height: 100px;
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: #1976d2;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 18px;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #1565c0;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>简历</h1>
    <form method="post" action="${pageContext.request.contextPath}/user/register">
        <label>
            姓名：
            <input type="text" name="name" required>
        </label>
        <label>
            出生日期：
            <input type="date" name="date" required>
        </label>
        <label>
            学校：
            <input type="text" name="school" required>
        </label>
        <label>
            描述：
            <textarea name="description" required></textarea>
        </label>
        <button type="submit">提交</button>
    </form>
</div>

</body>
</html>
