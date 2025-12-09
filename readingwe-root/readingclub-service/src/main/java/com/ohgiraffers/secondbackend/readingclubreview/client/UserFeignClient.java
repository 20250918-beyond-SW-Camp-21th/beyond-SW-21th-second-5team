package com.ohgiraffers.secondbackend.readingclubreview.client;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/user/userId/{userId}")



}
