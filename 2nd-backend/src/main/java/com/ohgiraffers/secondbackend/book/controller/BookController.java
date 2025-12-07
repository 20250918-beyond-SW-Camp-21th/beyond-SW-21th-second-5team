package com.ohgiraffers.secondbackend.book.controller;

import com.ohgiraffers.secondbackend.book.dto.response.BookResponseDTO;
import com.ohgiraffers.secondbackend.book.entity.Book;
import com.ohgiraffers.secondbackend.book.service.BookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;


    @GetMapping("/booklist")
    public List<Book> printBookList() {
        return bookService.findAll();
    }

    @GetMapping("/booklist/{bookid}")
    public BookResponseDTO printBookById(@PathVariable Long bookid){
        return bookService.findById(bookid);
    }
}
