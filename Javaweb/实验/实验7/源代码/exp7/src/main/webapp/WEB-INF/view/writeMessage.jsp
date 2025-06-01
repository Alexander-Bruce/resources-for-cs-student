<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>写入消息</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: linear-gradient(135deg, #6e7a99, #e1e9f0); /* 渐变背景 */
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .write-message-container {
      background-color: #ffffff;
      padding: 25px 35px;
      border-radius: 12px;
      box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
      text-align: center;
      width: 80%;
      max-width: 650px;
      transition: transform 0.3s ease;
    }

    .write-message-container:hover {
      transform: scale(1.02); /* 鼠标悬停时放大 */
    }

    .write-message-container h2 {
      color: #2d3e50;
      margin-bottom: 25px;
      font-size: 28px;
      font-weight: 600;
    }

    .write-message-container form p {
      margin: 15px 0;
      font-size: 16px;
      color: #555;
    }

    .write-message-container input[type="text"],
    .write-message-container textarea {
      width: 90%;
      padding: 10px;
      margin: 8px 0;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 16px;
      color: #333;
      transition: border-color 0.3s ease;
    }

    .write-message-container input[type="text"]:focus,
    .write-message-container textarea:focus {
      border-color: #007bff; /* 输入框聚焦时边框颜色 */
      outline: none;
    }

    .write-message-container input[type="submit"],
    .write-message-container input[type="reset"] {
      width: 45%;
      padding: 12px;
      margin: 12px 6px;
      border: none;
      border-radius: 6px;
      background-color: #28a745;
      color: white;
      font-size: 18px;
      cursor: pointer;
      transition: background-color 0.3s ease, transform 0.2s ease;
    }

    .write-message-container input[type="submit"]:hover,
    .write-message-container input[type="reset"]:hover {
      background-color: #218838;
      transform: translateY(-2px); /* 按钮悬停时的轻微抬升效果 */
    }

    .write-message-container input[type="reset"]:hover {
      background-color: #dc3545;
    }

  </style>
</head>
<body>
<div class="write-message-container">
  <h2>写消息</h2>
  <form method="post" action="${pageContext.request.contextPath}/message/save">
    <p>
      <label for="title">主题：</label>
      <input type="text" id="title" name="title" placeholder="请输入消息主题" required>
    </p>
    <p>
      <label for="content">内容：</label>
      <textarea id="content" name="content" rows="4" placeholder="请输入消息内容" required></textarea>
    </p>
    <p>
      <input type="submit" value="发送消息">
      <input type="reset" value="重置">
    </p>
  </form>
</div>
</body>
</html>
