package com.ohgiraffers.bookservice.secondbackend.bookreport.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/userId/{userId}")
    UserProfileResponseDto getUserById(@PathVariable("userId") Long userId);
}
