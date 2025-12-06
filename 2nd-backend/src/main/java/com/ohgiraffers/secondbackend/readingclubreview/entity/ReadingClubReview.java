package com.ohgiraffers.secondbackend.readingclubreview.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "reading_club_review")
public class ReadingClubReview {

    @Id
    @Column(name = "club_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Column(name = "club_id", nullable = false) // reading_club 테이블 fk
    private long clubId;

    @Column(name = "writer_id", nullable = false) // user 테이블 fk
    private long writerId;

    @Column(name = "review_title", nullable = false)
    private String reviewTitle;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "like_total")
    private long likeTotal = 0L;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public ReadingClubReview(long clubId,
                             long writerId,
                             String reviewTitle,
                             String reviewContent) {
        this.clubId = clubId;
        this.writerId = writerId;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.likeTotal = 0L;
    }
}
