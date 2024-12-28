package com.example.rct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 비동기
public class RctApplication {

    public static void main(String[] args) {
        SpringApplication.run(RctApplication.class, args);
    }

}
