package backend.controller;

import backend.service.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String isLogin(String username, String password, Model model, HttpServletRequest request) {
        return userService.login(username, password, model, request);
    }
}
