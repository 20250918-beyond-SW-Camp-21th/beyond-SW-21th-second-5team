package com.ohgiraffers.secondbackend.readingclubreview.dto.response;

import com.ohgiraffers.secondbackend.readingclubreview.entity.ReviewComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewCommentResponseDTO {

    private Long reviewCommentId;
    private Long clubReviewId;
    private Long writerId;        // 또는 userId
    private Long parentCommentId;
    private String commentDetail;
    private LocalDateTime createdAt;
    private boolean deleteComment;

    public static ReviewCommentResponseDTO from(ReviewComment comment) {
        return ReviewCommentResponseDTO.builder()
                .reviewCommentId(comment.getReviewCommentId())
                .clubReviewId(comment.getReview().getReviewId())
                .writerId(comment.getUser().getId())
                .parentCommentId(
                        comment.getParent() != null
                                ? comment.getParent().getReviewCommentId()
                                : null
                )
                .commentDetail(comment.getCommentDetail())
                .createdAt(comment.getCreatedAt())
                .deleteComment(comment.isDeleteComment())
                .build();
    }
}

