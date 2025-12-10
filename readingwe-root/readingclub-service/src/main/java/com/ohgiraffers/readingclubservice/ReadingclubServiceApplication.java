package com.ohgiraffers.readingclubservice;

import com.ohgiraffers.secondbackend.readingclub.client.EmailFeignClient;
import com.ohgiraffers.secondbackend.readingclub.client.UserFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ohgiraffers.secondbackend"})
@EnableFeignClients(basePackageClasses = {EmailFeignClient.class, UserFeignClient.class})
public class ReadingclubServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadingclubServiceApplication.class, args);
    }

}
