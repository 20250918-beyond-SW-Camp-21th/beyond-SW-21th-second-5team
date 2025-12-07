package com.ohgiraffers.secondbackend.readingclubreview.repository;

import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingClubReviewRepository extends JpaRepository<ReadingClubReview, Long> {

    boolean existsByClubIdAndWriterId(long clubId, long writerId);
}
