package com.ohgiraffers.secondbackend.bookreport.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class BookReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookReportId;

    private Long bookId;

    private Long userId;

    private String bookTitle;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
