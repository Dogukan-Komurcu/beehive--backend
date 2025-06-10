package com.example.beehiveproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.beehiveproject.repository")
public class BeehiveprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeehiveprojectApplication.class, args);
    }

}
