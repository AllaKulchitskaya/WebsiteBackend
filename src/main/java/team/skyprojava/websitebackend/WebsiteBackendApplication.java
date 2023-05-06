package team.skyprojava.websitebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"team.skyprojava.websitebackend.mapper"})
public class WebsiteBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteBackendApplication.class, args);
    }

}
