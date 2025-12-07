package com.ohgiraffers.secondbackend.book.service;

import com.ohgiraffers.secondbackend.book.dto.response.BookResponseDTO;
import com.ohgiraffers.secondbackend.book.entity.Book;
import com.ohgiraffers.secondbackend.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService( BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    private BookResponseDTO convert(Book book) {
        return BookResponseDTO.builder()
                .bookid(book.getBookId())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishedDate(book.getPublishedDate())
                .category(book.getCategory())
                .build();
    }


    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public BookResponseDTO findById(Long bookId){
        return convert(bookRepository.findById(bookId).orElseThrow());
    }


}
