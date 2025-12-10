package com.ohgiraffers.secondbackend.client;

import com.ohgiraffers.secondbackend.client.dto.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping("/userId/{userId}")
    UserProfileResponse getUserProfileById(@PathVariable("userId") Long userId);

    @GetMapping("/username/{username}")
    UserProfileResponse getUserProfileByUsername(@PathVariable("username") String username);
}
