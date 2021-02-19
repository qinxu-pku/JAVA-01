import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ClassesBean.class)
public class ClassesAutoConfiguration {
}
