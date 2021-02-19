package config;

import entity.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentApplicationContext {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("entity");
        Student student = (Student) context.getBean("student");
        System.out.println(student.toString());
    }
}
