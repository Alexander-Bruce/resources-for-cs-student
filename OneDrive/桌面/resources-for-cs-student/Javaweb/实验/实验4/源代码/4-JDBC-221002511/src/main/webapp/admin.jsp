<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="experiment3.backend.beans.DBConnection" %>
<html>
<head>
    <title>管理员控制面板</title>
    <style>
        body {
            text-align: center; /* 将整个页面文本居中 */
        }
        table {
            margin: 0 auto; /* 表格水平居中 */
            border-collapse: collapse; /* 合并边框 */
        }
        th, td {
            padding: 10px; /* 单元格内边距 */
            border: 1px solid #000; /* 边框样式 */
        }
    </style>
</head>
<body>
<h2>所有用户</h2>
<table>
    <tr>
        <th>用户名</th>
        <th>密码</th>
        <th>年龄</th>
        <th>性别</th>
        <th>照片位置</th>
        <th>描述</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    <%
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT username, password, age, sexy, picturelocation, description, created_time FROM user"; // 查询所有字段
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                int age = rs.getInt("age");
                String sexy = rs.getString("sexy");
                String pictureLocation = rs.getString("picturelocation");
                String description = rs.getString("description");
                Timestamp createdTime = rs.getTimestamp("created_time");
    %>
    <tr>
        <td><%= username %></td>
        <td><%= password %></td>
        <td><%= age %></td>
        <td><%= sexy %></td>
        <td><%= pictureLocation %></td>
        <td><%= description %></td>
        <td><%= createdTime %></td>
        <td>
            <a href="DeleteUserServlet?username=<%= username %>">删除</a>
            <a href="EditUser.jsp?username=<%= username %>">修改</a>
        </td>
    </tr>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 确保资源被释放
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    %>
</table>
</body>
</html>
