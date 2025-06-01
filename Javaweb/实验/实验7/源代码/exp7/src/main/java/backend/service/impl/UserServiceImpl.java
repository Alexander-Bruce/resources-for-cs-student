package backend.service.impl;

import backend.dao.UserDao;
import backend.model.User;
import backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String login(String username, String password, Model model, HttpServletRequest request) {

        User user = userDao.getUserByUserName(username, password);

        if (user == null) {
            model.addAttribute("error", "用户名或密码错误，请重试！");
            return "forward:/login";
        }

        request.getSession().setAttribute("uid", user.getId());
        return "forward:/message";
    }

    @Override
    public String register(String username, String pwd1, String pwd2, Model model) {

        if (!pwd1.equals(pwd2)) {
            model.addAttribute("error", "两次密码输入不一致，请重新输入！");
            return "forward:/register";
        }

        User user = userDao.getUserByRawUserName(username);
        if(user != null) {
            model.addAttribute("error", "用户名重复，请重新输入！");
            return "forward:/register";
        }

        userDao.insert(
                User.builder()
                        .username(username)
                        .password(pwd1)
                        .build()
        );

        return "forward:/login";
    }
}
