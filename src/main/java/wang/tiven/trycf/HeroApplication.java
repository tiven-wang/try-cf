package wang.tiven.trycf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "wang.tiven.trycf.repository")
public class HeroApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HeroApplication.class, args);
    }

}
