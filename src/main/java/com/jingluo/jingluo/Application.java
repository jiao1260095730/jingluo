package com.jingluo.jingluo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 //启用swagger
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
