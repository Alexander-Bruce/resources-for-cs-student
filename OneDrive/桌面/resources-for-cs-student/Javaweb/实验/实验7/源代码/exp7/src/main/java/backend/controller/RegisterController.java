package backend.controller;

import backend.service.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String getRegisterPage() {
        return "register";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String register(String username, String pwd1, String pwd2, Model model) {
        return userService.register(username, pwd1, pwd2, model);
    }
}
