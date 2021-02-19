package config;

import entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StudentComponent {

    @Bean
    public Student student(){
        return new Student(1,"lisi",20);
    }
}
