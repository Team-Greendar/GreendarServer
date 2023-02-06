package greendar;

import greendar.global.config.properties.AppProperties;
import greendar.global.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
public class GreendarApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreendarApplication.class, args);
    }

}
