package backend.mapper;

import backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User login(String username, String password);

    void register(String username, String password);

    User getUser(String username);
}
