package com.ohgiraffers.secondbackend.user.controller;

import com.ohgiraffers.secondbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> request){
        String username=request.get("username");
        String password= request.get("password");
        String nickname=request.get("nickname");
        try{
            userService.signup(username,password,nickname);
            return ResponseEntity.ok("등록 성공");
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
