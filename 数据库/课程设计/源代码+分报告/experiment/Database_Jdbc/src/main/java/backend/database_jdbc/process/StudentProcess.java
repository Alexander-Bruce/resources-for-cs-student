package backend.database_jdbc.process;

import backend.database_jdbc.bean.StudentBean;
import backend.database_jdbc.entity.Student;

import java.util.Date;

public class StudentProcess {
    public static void main(String[] args){
        StudentBean studentBean = new StudentBean();
        // get all students
        System.out.println("All students:");
        studentBean.getStudentDAO().getAllStudents().forEach(student -> System.out.println(student.toString()));

        // get student by sid
        System.out.println("Student with sid 0591:");
        System.out.println(studentBean.getStudentDAO().getStudent("0591").toString());

        // add student
        System.out.println("Add student with sid 0692:");
        studentBean.getStudentDAO().addStudent(new Student("0692", "张三", "男", "软件工程", "1999-01-01", "12345678901"));
        System.out.println(studentBean.getStudentDAO().getStudent("0692").toString());

        // update student
        System.out.println("Update student with sid 0692:");
        studentBean.getStudentDAO().updateStudent(new Student("0692", "李四", "男", "软件工程", "1999-01-01", "12345678901"));
        System.out.println(studentBean.getStudentDAO().getStudent("0692").toString());

        // delete student
        System.out.println("Delete student with sid 0692:");
        studentBean.getStudentDAO().deleteStudent("0692");
        studentBean.getStudentDAO().getAllStudents().forEach(student -> System.out.println(student.toString()));
    }
}
