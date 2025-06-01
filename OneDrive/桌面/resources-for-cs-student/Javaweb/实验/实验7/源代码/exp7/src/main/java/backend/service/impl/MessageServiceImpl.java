package backend.service.impl;

import backend.dao.MessageDao;
import backend.dao.UserDao;
import backend.model.Message;
import backend.model.User;
import backend.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    @Override
    public void getMessages(Model model) {
        List<Message> messageList = messageDao.getMessageList();
        List<User> userList = userDao.getAllUsers();
        List<String> usernameList = new ArrayList<>();
        for (Message message : messageList) {
            String username = null;
            Integer uid = message.getUserId();
            for (User user : userList) {
                if (Objects.equals(user.getId(), uid)) {
                    username = user.getUsername();
                    break;
                }
            }
            usernameList.add(username);
        }
        model.addAttribute("messages", messageList);
        model.addAttribute("usernames", usernameList);
    }

    @Override
    public void insertMessage(String title, String content, HttpServletRequest request) {
        messageDao.insertMessage(
                Message.builder()
                        .userId((Integer) request.getSession().getAttribute("uid"))
                        .title(title)
                        .content(content)
                        .build()
        );
    }
}
