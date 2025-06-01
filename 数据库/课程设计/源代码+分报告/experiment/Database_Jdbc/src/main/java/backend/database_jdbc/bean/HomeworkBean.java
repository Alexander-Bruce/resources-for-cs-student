package backend.database_jdbc.bean;

import backend.database_jdbc.dao.HomeworkDao;
import backend.database_jdbc.dao.impl.HomeworkDaoImpl;

public class HomeworkBean {
    public static HomeworkDao getHomeworkDAO() {
        return new HomeworkDaoImpl();
    }
}

