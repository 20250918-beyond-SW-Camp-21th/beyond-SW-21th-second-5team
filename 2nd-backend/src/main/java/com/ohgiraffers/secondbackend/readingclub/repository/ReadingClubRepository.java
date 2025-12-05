package com.ohgiraffers.secondbackend.readingclub.repository;

import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingClubRepository extends JpaRepository<ReadingClub, Integer> {

}
