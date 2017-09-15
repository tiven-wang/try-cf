package wang.tiven.trycf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;

@SpringBootApplication//(exclude = MongoDataAutoConfiguration.class)
public class HeroApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HeroApplication.class, args);
    }

}
