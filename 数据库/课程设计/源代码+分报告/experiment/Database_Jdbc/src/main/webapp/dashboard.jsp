<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
<head>
    <title>学生信息</title>
</head>
<body>

<h2>学生信息列表</h2>
<table border="1">
    <tr>
        <th>学号 (sid)</th>
        <th>姓名 (name)</th>
        <th>性别 (gender)</th>
        <th>专业班级 (major_class)</th>
        <th>出生日期 (birth)</th>
        <th>电话号码 (phone)</th>
    </tr>
    <%
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            conn = backend.database_jdbc.DBConnection.getConnection();
            String sql = "SELECT sid, name, gender, major_class, birth, phone FROM student";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // 遍历结果集
            while (rs.next()) {
                String sid = rs.getString("sid");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String major_class = rs.getString("major_class");
                Date birth = rs.getDate("birth");
                String phone = rs.getString("phone");
    %>
    <tr>
        <td><%= sid %></td>
        <td><%= name %></td>
        <td><%= gender %></td>
        <td><%= major_class %></td>
        <td><%= birth != null ? birth.toString() : "无" %></td>
        <td><%= phone != null ? phone : "无" %></td>
    </tr>
    <%
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            backend.database_jdbc.DBConnection.dbClose(conn, ps, rs);
        }
    %>
</table>

    <a href="${pageContext.request.contextPath}/insert-students">插入学生信息</a>
</body>
</html>

