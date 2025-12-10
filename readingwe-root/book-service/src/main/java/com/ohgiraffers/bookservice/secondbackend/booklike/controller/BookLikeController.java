package com.ohgiraffers.bookservice.secondbackend.booklike.controller;

import com.ohgiraffers.bookservice.secondbackend.booklike.dto.request.LikeApplyDTO;
import com.ohgiraffers.bookservice.secondbackend.booklike.dto.request.LikeCancelDTO;
import com.ohgiraffers.bookservice.secondbackend.booklike.dto.response.BookLikeResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.booklike.dto.response.BookRankingResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.booklike.service.BooklikeService;
import jakarta.servlet.http.HttpServletRequest;
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
            HttpServletRequest req,
            @PathVariable Long bookId
    ) {
        String rawuserid = req.getHeader("X-User-ID");
        long userid=Long.parseLong(rawuserid);

        LikeApplyDTO likeApplyDTO = new LikeApplyDTO(userid,bookId);
        BookLikeResponseDTO response = bookLikeService.likeBook(likeApplyDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/unlike/{bookId}")
    public ResponseEntity<Void> unlikeBook(
            HttpServletRequest req,
            @PathVariable Long bookId
    ) {
        String struserid = req.getHeader("X-User-ID");
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

