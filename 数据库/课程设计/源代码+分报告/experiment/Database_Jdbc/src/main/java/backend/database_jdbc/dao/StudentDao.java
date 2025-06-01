package backend.database_jdbc.dao;

import backend.database_jdbc.entity.Student;

import java.util.List;

public interface StudentDao {
    void addStudent(Student student);           // 添加学生
    void updateStudent(Student student);        // 更新学生信息
    void deleteStudent(String sid);             // 根据学号删除学生
    Student getStudent(String sid);              // 根据学号获取学生信息
    List<Student> getAllStudents();             // 获取所有学生信息
}

