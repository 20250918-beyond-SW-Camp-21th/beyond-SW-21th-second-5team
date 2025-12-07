package com.ohgiraffers.secondbackend.readingclubreview.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reading_club_review_like")
@Getter
@NoArgsConstructor
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_like_id")
    private Long reviewLikeId;

    @Column(name = "user_id", nullable = false)   // user 테이블 FK
    private Long userId;

    @Column(name = "club_review_id", nullable = false)  // club 테이블 FK
    private Long clubReviewId;

    @CreationTimestamp
    @Column(name = "like_datetime", nullable = false, updatable = false)
    private LocalDateTime likeDateTime;

    @Builder
    public ReviewLike(Long userId, Long clubReviewId) {
        this.userId = userId;
        this.clubReviewId = clubReviewId;
    }
}