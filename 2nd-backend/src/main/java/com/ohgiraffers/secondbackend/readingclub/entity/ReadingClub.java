package com.ohgiraffers.secondbackend.readingclub.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reading_club")
public class ReadingClub {
    @Id
    @Column(name = "club_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "club_name", nullable = false)
    private String name;
    @Column(name = "club_description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "club_status", nullable = false)
    private ReadingClubStatus status;
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(name = "host_user_id", nullable = false)  // user 테이블 fk
    private int userId;
    @Column(name = "category", nullable = false)      // category 테이블 fk
    private int categoryId;

}
