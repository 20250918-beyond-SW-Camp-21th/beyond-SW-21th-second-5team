package com.ohgiraffers.secondbackend.userlike.controller;

import com.ohgiraffers.secondbackend.userlike.dto.response.UserLikeResponseDTO;
import com.ohgiraffers.secondbackend.userlike.service.UserLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userlike")
@RequiredArgsConstructor
public class UserLikeController {

    private final UserLikeService userLikeService;

    @PostMapping("/like/{bookcategory}")
    public ResponseEntity<UserLikeResponseDTO> likeBook(
            HttpServletRequest req,
            @PathVariable String bookcategory
    ){

        String username = req.getHeader("X-User-Name");
        UserLikeResponseDTO response = userLikeService.likeBook(username,bookcategory);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unlike/{bookcategory}")
    public ResponseEntity<Void> unlikeBook(
            HttpServletRequest req,
            @PathVariable String bookcategory
    ){

        String username=req.getHeader("X-User-Name");
        userLikeService.unlikeBook(username,bookcategory);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserCategories(
            HttpServletRequest req
    ) {

        String username = req.getHeader("X-User-Name");
        List<String> categories = userLikeService.selectCategoryAll(username);

        return ResponseEntity.ok(categories);
    }


}
