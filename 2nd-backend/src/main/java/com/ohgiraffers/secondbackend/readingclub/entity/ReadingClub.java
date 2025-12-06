package com.ohgiraffers.secondbackend.readingclub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "reading_club")
public class ReadingClub {
    @Id
    @Column(name = "club_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    private long userId;
    @Column(name = "category", nullable = false)      // category 테이블 fk
    private long categoryId;

    @Builder
    public ReadingClub(String name, String description, long userId, long categoryId, ReadingClubStatus status) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.categoryId = categoryId;
        this.status = status;
    }

    public void changeStatus(ReadingClubStatus status) {
        this.status = status;
    }

    public void update(String name, String description, Long categoryId) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }
}
