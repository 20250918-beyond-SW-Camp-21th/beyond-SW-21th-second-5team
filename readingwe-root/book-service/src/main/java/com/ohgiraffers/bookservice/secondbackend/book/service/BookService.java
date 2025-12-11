package com.ohgiraffers.bookservice.secondbackend.book.service;

import com.ohgiraffers.bookservice.secondbackend.book.dto.request.AuthorRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.book.dto.request.CategoryRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.book.dto.request.TitleRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.book.dto.response.BookResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.book.entity.Book;
import com.ohgiraffers.bookservice.secondbackend.book.entity.BookCategory;
import com.ohgiraffers.bookservice.secondbackend.book.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publishedDate(book.getPublishedDate())
                .category(book.getCategory())
                .build();
    }


    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }


    @Transactional(readOnly = true)
    public BookResponseDTO findById(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        return convert(book);
    }


    @Transactional(readOnly = true)
    public Page<BookResponseDTO> findByTitle(String title, Pageable pageable) {

        return bookRepository.findByTitle(title, pageable)
                .map(this::convert);
    }



    @Transactional(readOnly = true)
    public Page<BookResponseDTO> findByCategory(BookCategory category, Pageable pageable) {

        return bookRepository.findByCategory(category, pageable)
                .map(this::convert);
    }



    @Transactional(readOnly = true)
    public Page<BookResponseDTO> findByAuthor(String author, Pageable pageable) {

        return bookRepository.findByAuthor(author, pageable)
                .map(this::convert);
    }





//    public List<BookResponseDTO> getSortedBooks(String sortBy, String direction) {
//
//        Sort sort = direction.equalsIgnoreCase("desc")
//                ? Sort.by(sortBy).descending()
//                : Sort.by(sortBy).ascending();
//
//        return bookRepository.findAll(sort).stream()
//                .map(this::convert)
//                .toList();
//    }



}
