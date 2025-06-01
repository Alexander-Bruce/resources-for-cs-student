package backend.dao;

import backend.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    @Select("select * from user where username = #{username} and password = #{password}")
    User getUserByUserName(@Param("username") String username, @Param("password") String password);

    @Select("select * from user where username = #{username}")
    User getUserByRawUserName(@Param("username") String username);

    void insert(User user);

    List<User> getAllUsers();
}
