package com.ohgiraffers.secondbackend.user.controller;

import com.ohgiraffers.secondbackend.user.dto.request.PasswordUpdateDTO;
import com.ohgiraffers.secondbackend.user.dto.request.ProfileUpdateDTO;
import com.ohgiraffers.secondbackend.user.dto.response.UserProfileResponse;
import com.ohgiraffers.secondbackend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest req
    ) {
        String username = req.getHeader("X-User-Name");
        userService.logout(username);
        return ResponseEntity.ok("logout 성공");
    }
    @PatchMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(@RequestHeader("Authorization") String authorizationHeader
            , @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("받은게 없거나 잘못된 헤더입니다.");
        }

        String accessToken = authorizationHeader.substring(7);


        try{
            userService.updateNickname(accessToken,profileUpdateDTO);
            return ResponseEntity.ok("변경 성공");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("변경 실패"+e.getMessage());
        }
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String authorizationHeader
            , @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("받은게 없거나 잘못된 헤더입니다.");
        }

        String accessToken = authorizationHeader.substring(7);


        try{
            userService.updatePassword(accessToken,passwordUpdateDTO);
            return ResponseEntity.ok("변경 성공");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("변경 실패"+e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserProfileResponse> getUserProfileByUsername(@PathVariable("username") String username) {
        UserProfileResponse profile = userService.getProfileByUsername(username);
        return ResponseEntity.ok(profile);

    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(@PathVariable("userId") Long userId) {
        UserProfileResponse profile = userService.getProfileById(userId);
        return ResponseEntity.ok(profile);
    }


}