package com.ohgiraffers.secondbackend.readingclubreview.controller;

import com.ohgiraffers.secondbackend.readingclubreview.dto.request.ReadingClubReviewRequestDTO;
import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReadingClubReviewResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.service.ReadingClubReviewService;
//import com.oracle.svm.core.annotate.Delete;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReadingClubReviewController {

    private final ReadingClubReviewService reviewService;

    @PostMapping("/clubId/{clubId}")
    public ResponseEntity<ReadingClubReviewResponseDTO> createReview(@PathVariable Long clubId, @RequestBody ReadingClubReviewRequestDTO request,
                                                                     Authentication authentication){

        // 로그인한 사용자의 username 가져오기
        String username = authentication.getName();

        ReadingClubReviewResponseDTO response = reviewService.createReview(clubId, request, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/reviewId/{reviewId}")
    public ResponseEntity<ReadingClubReviewResponseDTO> modifyReview(@PathVariable Long reviewId,
                                                                     @RequestBody ReadingClubReviewRequestDTO request,
                                                                     Authentication authentication){

        String username = authentication.getName();

        ReadingClubReviewResponseDTO response = reviewService.modifyReview(reviewId, request, username);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reviewId/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId,
                                             Authentication authentication)
    {
        String username = authentication.getName();

        reviewService.deleteReview(reviewId, username);

        return ResponseEntity.noContent().build();

    }

}
