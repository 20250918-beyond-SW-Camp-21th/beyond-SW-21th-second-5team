package com.ohgiraffers.secondbackend.booklike.controller;

import com.ohgiraffers.secondbackend.booklike.dto.request.LikeApplyDTO;
import com.ohgiraffers.secondbackend.booklike.dto.request.LikeCancelDTO;
import com.ohgiraffers.secondbackend.booklike.dto.response.BookLikeResponseDTO;
import com.ohgiraffers.secondbackend.booklike.dto.response.BookRankingResponseDTO;
import com.ohgiraffers.secondbackend.booklike.service.BooklikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booklike")
public class BookLikeController {

    private final BooklikeService bookLikeService;

    @PostMapping("/like/{bookId}")
    public ResponseEntity<BookLikeResponseDTO> likeBook(
            @RequestHeader("X-User-ID") String struserid,
            @PathVariable Long bookId
    ) {

        long userid=Long.parseLong(struserid);

        LikeApplyDTO likeApplyDTO = new LikeApplyDTO(userid,bookId);
        BookLikeResponseDTO response = bookLikeService.likeBook(likeApplyDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unlike/{bookId}")
    public ResponseEntity<Void> unlikeBook(
            @RequestHeader("X-User-ID") String struserid,
            @PathVariable Long bookId
    ) {
        long userId= Long.parseLong(struserid);

        LikeCancelDTO likeCancelDTO = new LikeCancelDTO(userId,bookId);

        bookLikeService.deleteLike(likeCancelDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<BookRankingResponseDTO>> getBookRanking() {
        return ResponseEntity.ok(bookLikeService.getBookRanking());
    }

}

