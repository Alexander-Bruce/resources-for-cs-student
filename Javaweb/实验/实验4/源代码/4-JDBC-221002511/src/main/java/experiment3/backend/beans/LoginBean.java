package experiment3.backend.beans;

import java.sql.*;

public class LoginBean {
    private String username;
    private String pwd;
    //setter函数
    public void setUsername(String s){
        username = s;
    }
    public void setPwd(String p){
        pwd = p;
    }

    //getter函数
    public String getUsername(){
        return(username);
    }
    public String getPwd(){
        return(pwd);
    }

    //到logintable表中查找是否存在给定username和pwd的用户
    //如果存在，就返回真
    //否则返回假
    static public boolean isExistsInLogintable(String un, String p){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn=DBConnection.getConnection();
            String sql = "select * from user where username=? and password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, un);
            ps.setString(2, p);
            rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                DBConnection.dbClose(conn, ps, rs); //释放数据库连接资源
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

}
