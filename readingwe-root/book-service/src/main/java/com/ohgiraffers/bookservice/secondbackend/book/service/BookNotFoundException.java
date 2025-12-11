package com.ohgiraffers.bookservice.secondbackend.book.service;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long bookId) {
        throw new RuntimeException("Book not found: " + bookId);
    }
}
