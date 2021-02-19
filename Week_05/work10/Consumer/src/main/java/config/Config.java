package config;


import org.springframework.context.annotation.Bean;
import Classee;

public class Config {
    @Bean
    public Classee classes() {
        return new Classee("81班");
    }
}
