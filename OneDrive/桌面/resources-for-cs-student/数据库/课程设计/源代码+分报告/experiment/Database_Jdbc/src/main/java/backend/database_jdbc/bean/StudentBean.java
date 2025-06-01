package backend.database_jdbc.bean;


import backend.database_jdbc.dao.StudentDao;
import backend.database_jdbc.dao.impl.StudentDaoImpl;

public class StudentBean {
    public static StudentDao getStudentDAO() {
        return new StudentDaoImpl();
    }
}
