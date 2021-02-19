package config;

import entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentXmlConfig {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("springApplication.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());
    }
}
