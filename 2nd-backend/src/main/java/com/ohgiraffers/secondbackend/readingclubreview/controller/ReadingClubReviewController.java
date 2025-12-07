package com.ohgiraffers.secondbackend.readingclubreview.controller;

import com.ohgiraffers.secondbackend.readingclubreview.dto.request.ReadingClubReviewRequestDTO;
import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReadingClubReviewResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.service.ReadingClubReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reading-club-review")
@RequiredArgsConstructor
public class ReadingClubReviewController {

    private final ReadingClubReviewService reviewService;

    @PostMapping("/{clubId}/review-create")
    public ResponseEntity<ReadingClubReviewResponseDTO> createReview(@PathVariable long clubId, @RequestBody ReadingClubReviewRequestDTO request,
                                                                     Authentication authentication){

        // 로그인한 사용자의 username 가져오기
        String username = authentication.getName();

        ReadingClubReviewResponseDTO response = reviewService.createReview(clubId, request, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/review-modify/{reviewId}")
    public ResponseEntity<ReadingClubReviewResponseDTO> modifyReview(@PathVariable Long reviewId,
                                                                     @RequestBody ReadingClubReviewRequestDTO request,
                                                                     Authentication authentication){

        String username = authentication.getName();

        ReadingClubReviewResponseDTO response = reviewService.modifyReview(reviewId, request, username);

        return ResponseEntity.ok(response);
    }
}
