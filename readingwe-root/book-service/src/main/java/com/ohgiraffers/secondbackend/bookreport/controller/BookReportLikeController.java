package com.ohgiraffers.secondbackend.bookreport.controller;

import com.ohgiraffers.secondbackend.bookreport.dto.request.BookReportLikeRequestDTO;
import com.ohgiraffers.secondbackend.bookreport.dto.response.BookReportLikeResponseDTO;
import com.ohgiraffers.secondbackend.bookreport.service.BookReportLikeService;
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
