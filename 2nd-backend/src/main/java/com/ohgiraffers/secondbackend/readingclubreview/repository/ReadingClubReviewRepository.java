package com.ohgiraffers.secondbackend.readingclubreview.repository;

import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClub;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import com.ohgiraffers.secondbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReadingClubReviewRepository extends JpaRepository<ReadingClubReview, Long> {

    boolean existsByClubIdAndWriterId(ReadingClub clubId, User writerId);

    Optional<ReadingClubReview> findByReviewIdAndWriterId(Long reviewId, User writerId);


}
