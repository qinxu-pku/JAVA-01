import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PropertySource("classpath:application.yml")
public class Classee implements InitializingBean {

    private String className;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化Class 班级:" + className);
    }
}
