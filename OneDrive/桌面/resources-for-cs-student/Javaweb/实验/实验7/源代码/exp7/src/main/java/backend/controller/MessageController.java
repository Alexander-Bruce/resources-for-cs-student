package backend.controller;

import backend.service.MessageService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("")
    public String getMessages(Model model) {
        messageService.getMessages(model);
        return "message";
    }

    @RequestMapping("/write")
    public String getMessageWritingPage() {
        return "writeMessage";
    }

    @RequestMapping("/save")
    public String insertMessage(String title, String content, HttpServletRequest request) {
        messageService.insertMessage(title, content, request);
        return "redirect:/message";
    }

}
