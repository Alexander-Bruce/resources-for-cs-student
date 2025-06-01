package backend.database_jdbc.dao;

import backend.database_jdbc.entity.Homework;

import java.sql.SQLException;
import java.util.List;

public interface HomeworkDao {
    void addHomework(Homework homework) throws SQLException;
    void updateHomework(Homework homework) throws SQLException;
    void deleteHomework(String cid, String sid) throws SQLException;
    Homework getHomeworkById(String cid, String sid) throws SQLException;
    List<Homework> getAllHomeworks() throws SQLException;
}

