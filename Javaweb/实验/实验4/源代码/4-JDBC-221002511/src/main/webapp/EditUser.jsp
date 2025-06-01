<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="experiment3.backend.beans.DBConnection" %>
<html>
<head>
    <title>修改用户信息</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        form {
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<%
    String username = request.getParameter("username");
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    try {
        conn = DBConnection.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        rs = ps.executeQuery();
        if (rs.next()) {
%>
<form action="UpdateUserServlet" method="post">
    <input type="hidden" name="username" value="<%= username %>" />
    <p>密码：<input type="text" name="password" value="<%= rs.getString("password") %>" /></p>
    <p>年龄：<input type="number" name="age" value="<%= rs.getInt("age") %>" /></p>
    <p>性别：<input type="text" name="sexy" value="<%= rs.getString("sexy") %>" /></p>
    <p>照片位置：<input type="text" name="pictureLocation" value="<%= rs.getString("picturelocation") %>" /></p>
    <p>描述：<input type="text" name="description" value="<%= rs.getString("description") %>" /></p>
    <p>创建时间：<input type="text" name="createdTime" value="<%= rs.getString("created_time") %>" /></p>
    <input type="submit" value="提交修改" />
</form>
<%
        } else {
            System.out.println("用户不存在");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (ps != null) try { ps.close(); } catch (SQLException e) {}
        if (conn != null) try { conn.close(); } catch (SQLException e) {}
    }
%>
</body>
</html>
