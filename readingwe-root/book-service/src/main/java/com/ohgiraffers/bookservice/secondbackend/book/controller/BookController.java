package com.ohgiraffers.bookservice.secondbackend.book.controller;

import com.ohgiraffers.bookservice.secondbackend.book.dto.request.AuthorRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.book.dto.request.CategoryRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.book.dto.request.TitleRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.book.dto.response.BookResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.book.entity.Book;
import com.ohgiraffers.bookservice.secondbackend.book.entity.BookCategory;
import com.ohgiraffers.bookservice.secondbackend.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;


    @GetMapping("/booklist")
    public ResponseEntity<Page<Book>> printBookList(
            @PageableDefault(page=0, size=10) Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @GetMapping("/booklist/{bookid}")
    public BookResponseDTO printBookById(@PathVariable Long bookid){
        return bookService.findById(bookid);
    }

    @GetMapping("/booklist/title/{booktitle}")
    public Page<BookResponseDTO> printBookByTitle(
            @PathVariable String booktitle,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return bookService.findByTitle(booktitle, pageable);
    }


    @GetMapping("/booklist/category/{category}")
    public Page<BookResponseDTO> printBookByCategory(
            @PathVariable String category,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        BookCategory bookcategory = BookCategory.valueOf(category);
        return bookService.findByCategory(bookcategory, pageable);
    }


    @GetMapping("/booklist/author/{author}")
    public Page<BookResponseDTO> printBookByAuthor(
            @PathVariable String author,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return bookService.findByAuthor(author, pageable);
    }



    //Feign을 위한 api
    @GetMapping("/categories")
    public List<String>getAllCategories(){
        return Arrays.stream(BookCategory.values())
                .map(Enum::name)
                .toList();
    }
}
