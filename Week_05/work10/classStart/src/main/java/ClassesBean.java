import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassesBean {

    @Bean
    public Classee classes() {

        return new Classee("81班");
    }
}
