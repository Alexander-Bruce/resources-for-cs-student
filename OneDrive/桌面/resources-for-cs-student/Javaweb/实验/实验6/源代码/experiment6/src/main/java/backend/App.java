package backend;

import backend.entity.Member;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        // Member member1 = (Member) getBeanByName("Member1");
        Member member2 = (Member) getBeanByName("Member2");

        // System.out.println(member1);
        System.out.println(member2);
    }


    public static Object getBeanByName(String name){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        return context.getBean(name);
    }
}
