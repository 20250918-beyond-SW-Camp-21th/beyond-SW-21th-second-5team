package com.ohgiraffers.readingclubservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ohgiraffers.secondbackend"})
@EnableFeignClients(basePackages = {"com.ohgiraffers.secondbackend"})
public class ReadingclubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadingclubServiceApplication.class, args);
    }

}
