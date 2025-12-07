package com.ohgiraffers.secondbackend.bookreport.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "book_report")
public class BookReport {
    @Id
    @Column(name = "book_report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookReportId;      //독후감 id(pk)

    @Column(name = "book_id")
    private Long bookId;        //도서 id(fk)

    @Column(name = "user_id")
    private Long userId;      //글 작성자 id(fk)

    @Column(name = "title")
    private String title;   //제목

    @Column(name = "description")
    private String description;     //내용

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;    //생성일

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public BookReport(Long bookId, Long userId, String title, String description){
        this.bookId = bookId;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    // setter대체용
    public void update(String title, String description){
        this.title = title;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
