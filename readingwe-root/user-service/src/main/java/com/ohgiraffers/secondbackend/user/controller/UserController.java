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
    public ResponseEntity<String> updateNickname(
            HttpServletRequest req
            , @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        String username = req.getHeader("X-User-Name");
        userService.updateNickname(username,profileUpdateDTO);
        return ResponseEntity.ok("닉네임 업데이트 성공");
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            HttpServletRequest req
            , @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        String username=req.getHeader("X-User-Name");
        userService.updatePassword(username,passwordUpdateDTO);
        return ResponseEntity.ok("비밀번호 업데이트 성공");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserProfileResponse> getUserProfileByUsername(
            @PathVariable("username") String newusername
            , HttpServletRequest req) {
        String username = req.getHeader("X-User-Name");
        UserProfileResponse userProfileResponse=
                userService.UpdateUsername(username,newusername);
        return ResponseEntity.ok(userProfileResponse);

    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(
            @PathVariable("userId") Long userId
            , HttpServletRequest req) {
        UserProfileResponse profile = userService.getProfileById(userId);
        return ResponseEntity.ok(profile);
    }


}