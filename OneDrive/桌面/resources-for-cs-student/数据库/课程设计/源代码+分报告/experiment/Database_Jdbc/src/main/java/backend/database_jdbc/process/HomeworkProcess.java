package backend.database_jdbc.process;

import backend.database_jdbc.bean.HomeworkBean;
import backend.database_jdbc.entity.Homework;

public class HomeworkProcess {
    public static void main(String[] args) {
        // get all homework
        System.out.println("All homework:");
        try{
            HomeworkBean.getHomeworkDAO().getAllHomeworks().forEach(homework -> System.out.println(homework.toString()));
        }catch(Exception e){
            e.printStackTrace();
        }

        // get homework by hid
        System.out.println("Homework with cid S001 and sid 0538");
        try{
            System.out.println(HomeworkBean.getHomeworkDAO().getHomeworkById("S001", "0538").toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        // update homework
        System.out.println("Update homework with cid S001 and sid 0538");
        try{
            HomeworkBean.getHomeworkDAO().updateHomework(new Homework("001", "001", 100, 100, 100));
            System.out.println(HomeworkBean.getHomeworkDAO().getHomeworkById("S001", "0538").toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        // delete homework
        System.out.println("Delete homework with cid S001 and sid 0538");
        try{
            HomeworkBean.getHomeworkDAO().deleteHomework("S001", "0538");
            HomeworkBean.getHomeworkDAO().getAllHomeworks().forEach(homework -> System.out.println(homework.toString()));
        }catch(Exception e){
            e.printStackTrace();
        }

        // add homework
        System.out.println("Add homework with cid S001 and sid 0538");
        try{
            HomeworkBean.getHomeworkDAO().addHomework(new Homework("S001", "0538", 100, 100, 100));
            HomeworkBean.getHomeworkDAO().getAllHomeworks().forEach(homework -> System.out.println(homework.toString()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
