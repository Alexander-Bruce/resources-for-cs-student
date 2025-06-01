package backend.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {

    void getMessages(Model model);

    void insertMessage(String title, String content, HttpServletRequest request);
}
