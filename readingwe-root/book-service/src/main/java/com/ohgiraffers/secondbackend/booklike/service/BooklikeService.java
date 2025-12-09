package com.ohgiraffers.secondbackend.booklike.service;


import com.ohgiraffers.secondbackend.book.entity.Book;
import com.ohgiraffers.secondbackend.book.repository.BookRepository;
import com.ohgiraffers.secondbackend.booklike.dto.request.LikeApplyDTO;
import com.ohgiraffers.secondbackend.booklike.dto.request.LikeCancelDTO;
import com.ohgiraffers.secondbackend.booklike.dto.response.BookLikeResponseDTO;
import com.ohgiraffers.secondbackend.booklike.dto.response.BookRankingResponseDTO;
import com.ohgiraffers.secondbackend.booklike.entity.BookLike;
import com.ohgiraffers.secondbackend.booklike.repository.BookLikeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BooklikeService {

    private final BookLikeRepository bookLikeRepository;
    private final BookRepository bookRepository;


    public BooklikeService(BookLikeRepository bookLikeRepository, BookRepository bookRepository) {
       this.bookLikeRepository = bookLikeRepository;
       this.bookRepository = bookRepository;
    }


    @Transactional
    public BookLikeResponseDTO likeBook(LikeApplyDTO likeApplyDTO) {

        Book book = bookRepository.findById(likeApplyDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));

        if (bookLikeRepository.existsByUserAndBook(likeApplyDTO.getUserId(), book)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 책입니다.");
        }

        BookLike bookLike = BookLike.builder()
                .user_id(likeApplyDTO.getUserId())
                .book(book)
                .build();

        BookLike savedBookLike = bookLikeRepository.save(bookLike);

        return BookLikeResponseDTO.builder()
                .bookLikeId(savedBookLike.getBooklike_id())
                .userId(likeApplyDTO.getUserId())
                .bookId(likeApplyDTO.getBookId())
                .build();
    }

    @Transactional
    public void deleteLike(LikeCancelDTO likeCancelDTO) {

        Book book = bookRepository.findById(likeCancelDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));

        BookLike bookLike = bookLikeRepository
                .findByUser_IdAndBook_BookId(likeCancelDTO.getUserId(),likeCancelDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("좋아요한 기록이 없습니다."));

        bookLikeRepository.delete(bookLike);
    }

    public List<BookRankingResponseDTO> getBookRanking() {
        List<Object[]> results = bookLikeRepository.findBooksOrderByLikeCount();

        return results.stream()
                .map(row -> {
                    Book book = (Book) row[0];
                    Long likeCount = (Long) row[1];

                    return BookRankingResponseDTO.builder()
                            .bookId(book.getBookId())
                            .title(book.getTitle())
                            .author(book.getAuthor())
                            .publisher(book.getPublisher())
                            .likeCount(likeCount)
                            .build();
                })
                .toList();
    }







}
