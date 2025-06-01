package experiment3.backend.beans;

import java.sql.*;

public class DetailedInfoBean {
    //声明表单元素对应的bean属性
    private String username;
    private int age;
    private String sexy;
    private String pictureLocation;

    //表单元素的setter函数
    public void setUsername(String u){
        username = u;
    }

    public void setAge(int a){
        age = a;
    }

    public void setSexy(String s){
        sexy = s;
    }

    public void setPictureLocation(String pl){
        pictureLocation = pl;
    }

    //表单元素的getter函数
    public String getUsername(){
        return(username);
    }

    public int getAge(){
        return(age);
    }

    public String getSexy(){
        return(sexy);
    }

    public String getPictureLocation(){
        return(pictureLocation);
    }

    //把bean属性的值插入detailedinfo表中
    //成功返回ture,失败返回false
    public boolean insertBeantoDB(){
        Connection conn = null;

        Statement st = null;
        try{
            conn = DBConnection.getConnection();
            st = conn.createStatement();
            //由bean属性拼接出Insert语句
            String sql1 = "insert into user(username,age,sexy,picturelocation) values("+
                    "\'"+username+"\'"+","+
                    age+","+
                    "\'"+sexy+"\'"+","+
                    "\'"+pictureLocation+"\'"+")";
            st.executeUpdate(sql1);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                conn.close();
                st.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return false;
    }

    //到数据库中查找是否存在给定username的客户
    //如果存在，就创建一个有关详细信息DetailedInfoBean的实例
    //否则返回Null
    static public DetailedInfoBean getDetailedInfoBean(String un){
        DetailedInfoBean bb = null;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DBConnection.getConnection();
            //如果logintable表中存在给定username的记录
            //则获取对应记录的每个字段的值，写入bean
            String sql = "select * from user where username=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, un);
            rs = ps.executeQuery();
            if(rs.next()) {
                bb = new DetailedInfoBean();
                bb.setUsername(rs.getString("username"));
                bb.setAge(rs.getInt("age"));
                bb.setSexy(rs.getString("sexy"));
                bb.setPictureLocation(rs.getString("picturelocation"));
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
        return bb;
    }

}

