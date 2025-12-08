package com.ohgiraffers.secondbackend.booklike.entity;

import com.ohgiraffers.secondbackend.book.entity.Book;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booklike")
public class BookLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booklike_id;

    @Column(nullable = false)
    private Long user_id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
