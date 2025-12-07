package com.ohgiraffers.secondbackend.readingclub.repository;

import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingClubMemberRepository extends JpaRepository<ReadingClubMember, Long> {
    boolean existsByClubIdAndUserId(Long clubId, Long userId);
    Optional<ReadingClubMember> findByClubIdAndUserId(Long clubId, Long userId);
}
