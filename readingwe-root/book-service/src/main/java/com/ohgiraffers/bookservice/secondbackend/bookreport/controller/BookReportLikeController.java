package com.ohgiraffers.bookservice.secondbackend.bookreport.controller;

import com.ohgiraffers.bookservice.secondbackend.bookreport.dto.request.BookReportLikeRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.bookreport.dto.response.BookReportLikeResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.bookreport.service.BookReportLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-report-like")
@RequiredArgsConstructor
public class BookReportLikeController {

    private final BookReportLikeService bookReportLikeService;

    @PostMapping("/{bookReportId}")
    public ResponseEntity<BookReportLikeResponseDTO> toggleLike(
            @PathVariable Long bookReportId,
            @RequestHeader("X-User-Id") String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader);
        return bookReportLikeService.toggleLike(bookReportId, userId);
    }
}
