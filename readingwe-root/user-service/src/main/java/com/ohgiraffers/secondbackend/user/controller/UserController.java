package com.ohgiraffers.secondbackend.user.controller;

import com.ohgiraffers.secondbackend.user.dto.request.PasswordUpdateDTO;
import com.ohgiraffers.secondbackend.user.dto.request.ProfileUpdateDTO;
import com.ohgiraffers.secondbackend.user.dto.response.UserProfileResponse;
import com.ohgiraffers.secondbackend.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest req
    ){
        String username = req.getHeader("X-User-Name");

        if(username==null || username.isBlank()){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("인증정보가 없습니다.");
        }

        userService.logout(username);
        return ResponseEntity.ok("logout 성공");
    }

    @PatchMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(
            HttpServletRequest req
            , @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        String username = req.getHeader("X-User-Name");

        if(username==null || username.isBlank()){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("인증정보가 없습니다.");
        }

        userService.updateNickname(username,profileUpdateDTO);
        return ResponseEntity.ok("닉네임 업데이트 성공");
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            HttpServletRequest req
            , @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        String username=req.getHeader("X-User-Name");

        if(username==null || username.isBlank()){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("인증정보가 없습니다.");
        }

        userService.updatePassword(username,passwordUpdateDTO);
        return ResponseEntity.ok("비밀번호 업데이트 성공");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserProfileResponse> getUserProfileByUsername(
            HttpServletRequest req,
            @PathVariable("username") String username) {

        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        String requester = req.getHeader("X-User-Name");
        if (requester == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!requester.equals(username)) {
            throw new ForbiddenException("본인만 조회 가능합니다.");
        }



        UserProfileResponse profile = userService.getProfileByUsername(username);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(
            HttpServletRequest req,
            @PathVariable("userId") Long userId) {

        if (userId == null ) {
            return ResponseEntity.badRequest().build();
        }

        String requester = req.getHeader("X-User-Name");

        if (requester == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        UserProfileResponse profile = userService.getProfileById(userId);
        return ResponseEntity.ok(profile);
    }


}