package com.ohgiraffers.secondbackend.readingclub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reading_club_join_request")
public class ReadingClubJoinRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "club_id", nullable = false)
    private Long clubId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "request_message", nullable = false)
    @Enumerated(EnumType.STRING)
    private JoinRequestStatus status; // PENDING, APPROVED, REJECTED
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setStatus(JoinRequestStatus status) {
        this.status = status;
    }
}
