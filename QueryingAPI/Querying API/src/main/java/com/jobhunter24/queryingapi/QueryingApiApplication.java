package com.jobhunter24.queryingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class QueryingApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(QueryingApiApplication.class, args);
    }
}