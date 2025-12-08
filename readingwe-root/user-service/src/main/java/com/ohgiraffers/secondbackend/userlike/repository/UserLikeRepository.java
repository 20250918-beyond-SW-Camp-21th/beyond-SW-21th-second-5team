package com.ohgiraffers.secondbackend.userlike.repository;

import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.userlike.entity.UserLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLikeEntity,Long> {
    List<UserLikeEntity> findByUser(User user);
    boolean existsByUserAndBookCategory(User user, String bookCategory);
    Optional<UserLikeEntity> findByUserAndBookCategory(User user, String category);
}
