package com.ohgiraffers.secondbackend.readingclubreview.repository;

import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReviewLike;
import com.ohgiraffers.secondbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

   //  리뷰에 이 유저가 좋아요를 눌렀는지?
    boolean existsByReviewAndUser(ReadingClubReview review, User user);

    // 이 리뷰에 이 유저가 누른 좋아요 삭제
    void deleteByReviewAndUser(ReadingClubReview review, User user);

    // (선택) 이 리뷰의 좋아요 개수
    long countByReview(ReadingClubReview review);
}
