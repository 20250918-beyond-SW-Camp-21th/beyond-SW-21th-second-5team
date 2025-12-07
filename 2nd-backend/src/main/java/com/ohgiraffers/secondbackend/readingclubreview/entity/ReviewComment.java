package com.ohgiraffers.secondbackend.readingclubreview.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reading_club_review_comment")
@Getter
@NoArgsConstructor
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_id")
    private Long reviewCommentId;

    @Column(name = "club_review_id", nullable = false)  // review 테이블 FK
    private Long clubReviewId;

    @Column(name = "user_id", nullable = false)         // user 테이블 FK
    private Long userId;

    @Column(name = "parent_comment_id")                 // 부모 댓글 (대댓글용), 루트 댓글이면 null
    private Long parentCommentId;

    @Column(name = "comment_detail", nullable = false)
    private String commentDetail;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "delete_comment", nullable = false)
    private boolean deleteComment = false;


    @Builder
    public ReviewComment(Long clubReviewId,
                         Long userId,
                         Long parentCommentId,
                         String commentDetail) {
        this.clubReviewId = clubReviewId;
        this.userId = userId;
        this.parentCommentId = parentCommentId; // null이면 루트 댓글
        this.commentDetail = commentDetail;
        this.deleteComment = false;
    }


}