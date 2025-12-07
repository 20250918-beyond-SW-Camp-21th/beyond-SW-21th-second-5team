package com.ohgiraffers.secondbackend.book.repository;

import com.ohgiraffers.secondbackend.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}
