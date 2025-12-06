package com.ohgiraffers.secondbackend.user.controller;

import com.ohgiraffers.secondbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization")String authorizationHeader){
        if(authorizationHeader==null || !authorizationHeader.startsWith("Bearer ")){
            return ResponseEntity.badRequest().body("받은게 없거나 잘못된 헤더입니다.");
        }

        String accessToken=authorizationHeader.substring(7);
        try{
            userService.logout(accessToken);
            return ResponseEntity.ok("logout 성공");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("로그아웃 실패: "+e.getMessage());
        }
    }
}
