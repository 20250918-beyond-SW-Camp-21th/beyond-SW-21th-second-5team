package com.ohgiraffers.secondbackend.readingclubreview.service;

import com.ohgiraffers.secondbackend.readingclubreview.dto.request.ReviewCommentRequestDTO;
import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReviewCommentResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReviewComment;
import com.ohgiraffers.secondbackend.readingclubreview.repository.ReadingClubReviewRepository;
import com.ohgiraffers.secondbackend.readingclubreview.repository.ReviewCommentRepository;
import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewCommentService {

    private final UserRepository userRepository;
    private final ReadingClubReviewRepository reviewRepository;
    private final ReviewCommentRepository commentRepository;

    @Transactional
    public ReviewCommentResponseDTO createReviewComment(Long reviewId,
                                                        ReviewCommentRequestDTO request,
                                                        String username) {

        // 1. 유저 찾기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));

        // 2. 리뷰 찾기
        ReadingClubReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

        // 3. 부모 댓글 검증 (있다면)
        Long parentCommentId = request.getParentCommentId();
        ReviewComment parent = null;

        if (parentCommentId != null) {
            parent = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));

            // 부모 댓글이 같은 리뷰에 속한 댓글인지
            if (!parent.getReview().getReviewId().equals(reviewId)) {
                throw new IllegalArgumentException("해당 리뷰의 댓글에만 대댓글을 작성할 수 있습니다.");
            }

            // 부모가 이미 대댓글이면 또 대댓글 불가
            if (parent.getParent() != null) {
                throw new IllegalStateException("대댓글에 또 대댓글을 작성할 수 없습니다.");
            }
        }

        // 4. 댓글 생성
        ReviewComment comment = ReviewComment.builder()
                .review(review)
                .user(user)
                .parent(parent)
                .commentDetail(request.getCommentDetail())
                .build();

        ReviewComment saved = commentRepository.save(comment);

        return ReviewCommentResponseDTO.from(saved);
    }

    @Transactional
    public ReviewCommentResponseDTO modifyComment(Long commentId,
                                                  ReviewCommentRequestDTO request,
                                                  String username) {
        // 1. username으로 유저 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        // 2. 이 유저가 작성한 해당 댓글 찾기 (아니면 권한 없음)
        ReviewComment comment = commentRepository
                .findByReviewCommentIdAndUser(commentId, user)
                .orElseThrow(() -> new AccessDeniedException("해당 댓글을 수정할 수 있는 권한이 없습니다."));

        // 내용 수정
        comment.updateContent(request.getCommentDetail());

        ReviewComment saved = commentRepository.save(comment);
        return ReviewCommentResponseDTO.from(saved);

    }

    @Transactional
    public void deleteComment(Long commentId, String username) {

        // 유저 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // 이 유저가 쓴 해당 댓글 찾기
        ReviewComment comment = commentRepository
                .findByReviewCommentIdAndUser(commentId, user)
                .orElseThrow(() -> new AccessDeniedException("해당 댓글을 삭제할 수 있는 권한이 없습니다."));

        comment.softDelete();
    }
}
