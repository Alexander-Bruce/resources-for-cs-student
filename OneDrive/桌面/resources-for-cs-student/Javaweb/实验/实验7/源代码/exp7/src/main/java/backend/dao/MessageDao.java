package backend.dao;

import backend.model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao {
    List<Message> getMessageList();

    void insertMessage(Message message);
}
