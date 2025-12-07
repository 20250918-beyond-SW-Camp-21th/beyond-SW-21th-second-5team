package com.ohgiraffers.secondbackend.readingclubreview.service;

import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReviewLikeToggleResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReviewLike;
import com.ohgiraffers.secondbackend.readingclubreview.repository.ReadingClubReviewRepository;
import com.ohgiraffers.secondbackend.readingclubreview.repository.ReviewLikeRepository;
import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final UserRepository userRepository;
    private final ReadingClubReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    @Transactional
    public ReviewLikeToggleResponseDTO toggleLike(Long reviewId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        ReadingClubReview review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        boolean alreadyLiked = reviewLikeRepository.existsByReviewAndUser(review, user);

        boolean nowLiked;
        if (alreadyLiked) {
            // 좋아요 취소
            reviewLikeRepository.deleteByReviewAndUser(review, user);
            review.decreaseLike();
            nowLiked = false;
        } else {
            // 좋아요 추가
            ReviewLike like = ReviewLike.builder()
                    .review(review)
                    .user(user)
                    .build();
            reviewLikeRepository.save(like);
            review.increaseLike();
            nowLiked = true;
        }
        long likeCount = reviewLikeRepository.countByReview(review);
        return new ReviewLikeToggleResponseDTO(nowLiked, likeCount);
    }
}
