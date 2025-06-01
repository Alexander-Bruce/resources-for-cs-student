<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的消息</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #6c85d1, #a1c4e6); /* 渐变背景 */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .message-container {
            background-color: #ffffff;
            padding: 35px;
            border-radius: 12px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15); /* 提升阴影效果 */
            text-align: center;
            width: 90%;
            max-width: 1100px;
            transition: transform 0.3s ease;
        }

        .message-container:hover {
            transform: scale(1.02); /* 鼠标悬停时放大 */
        }

        .message-container h2 {
            color: #333333;
            margin-bottom: 30px;
            font-size: 32px;
            font-weight: 700;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 25px;
            font-size: 16px;
        }

        table, th, td {
            border: 1px solid #e4e7ed;
        }

        th, td {
            padding: 18px;
            text-align: left;
        }

        th {
            background-color: #f1f6fb;
            color: #4b4f56;
            font-weight: 600;
        }

        tr:nth-child(even) {
            background-color: #f9fafb;
        }

        tr:hover {
            background-color: #f0f4f8;
        }

        .write-message-button {
            background-color: #1e7e34;
            color: white;
            padding: 16px 32px;
            border: none;
            border-radius: 8px;
            font-size: 20px;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .write-message-button:hover {
            background-color: #155d27;
            transform: translateY(-2px);
        }

        .no-messages {
            color: #888888;
            font-size: 18px;
            margin-top: 25px;
        }
    </style>
</head>
<body>
<div class="message-container">
    <h2>我的消息</h2>

    <c:choose>
        <c:when test="${not empty messages}">
            <table>
                <thead>
                <tr>
                    <th>消息ID</th>
                    <th>发送用户</th>
                    <th>主题</th>
                    <th>内容</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="message" items="${messages}" varStatus="status">
                    <tr>
                        <td>${message.id}</td>
                        <td>${usernames[status.index]}</td>
                        <td>${message.title}</td>
                        <td>${message.content}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-messages">暂无消息</div>
        </c:otherwise>
    </c:choose>

    <!-- 写消息按钮 -->
    <a href="${pageContext.request.contextPath}/message/write" class="write-message-button">写消息</a>
</div>
</body>
</html>
