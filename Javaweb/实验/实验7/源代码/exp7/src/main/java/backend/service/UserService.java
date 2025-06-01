package backend.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    String login(String username, String password, Model model, HttpServletRequest request);

    String register(String username, String pwd1, String pwd2, Model model);
}
