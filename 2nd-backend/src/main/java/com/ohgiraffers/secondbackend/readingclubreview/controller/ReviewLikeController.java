package com.ohgiraffers.secondbackend.readingclubreview.controller;

import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReviewLikeToggleResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.service.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review/like")
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    @PostMapping("/reviewId/{reviewId}")
    public ResponseEntity<ReviewLikeToggleResponseDTO> toggleLike(@PathVariable Long reviewId,
                                                                  Authentication authentication) {
        String username = authentication.getName();

        ReviewLikeToggleResponseDTO response =
                reviewLikeService.toggleLike(reviewId, username);
        return ResponseEntity.ok(response);

    }
}
