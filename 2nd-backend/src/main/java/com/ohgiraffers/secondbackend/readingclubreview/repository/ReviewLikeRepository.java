package com.ohgiraffers.secondbackend.readingclubreview.repository;

import com.ohgiraffers.secondbackend.readingclubreview.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    void deleteByClubReviewId(Long clubReviewId);
}
