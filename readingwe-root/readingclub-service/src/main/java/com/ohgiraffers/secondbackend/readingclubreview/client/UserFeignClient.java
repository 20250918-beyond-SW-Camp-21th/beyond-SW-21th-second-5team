package com.ohgiraffers.secondbackend.readingclubreview.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/user/userId/{userId}")
    UserProfileResponse getUserProfileByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/user/username/{username}")
    UserProfileResponse getUserProfileByUsername(@PathVariable("username") String username);





}
