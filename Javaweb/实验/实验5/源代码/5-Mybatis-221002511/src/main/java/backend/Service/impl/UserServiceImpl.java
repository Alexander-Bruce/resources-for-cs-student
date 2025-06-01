package backend.Service.impl;

import backend.Service.UserService;
import backend.entity.User;
import backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Boolean login(String username, String password) {
        User user = userMapper.login(username, password);
        return user != null;
    }

    @Override
    public String register(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) return "register";
        User user = userMapper.login(username, password);
        if (user == null) {
            userMapper.register(username, password);
            return "login";
        }
        return "login";
    }
}
