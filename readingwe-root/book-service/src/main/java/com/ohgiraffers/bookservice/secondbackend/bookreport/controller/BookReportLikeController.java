package com.ohgiraffers.bookservice.secondbackend.bookreport.controller;

import com.ohgiraffers.bookservice.secondbackend.bookreport.dto.request.BookReportLikeRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.bookreport.dto.response.BookReportLikeResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.bookreport.service.BookReportLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-report-like")
@RequiredArgsConstructor
public class BookReportLikeController {

    private final BookReportLikeService bookReportLikeService;

    @PostMapping("/{bookReportId}")
    public ResponseEntity<BookReportLikeResponseDTO> toggleLike(
            @RequestBody BookReportLikeRequestDTO request) {
        return bookReportLikeService.toggleLike(request);
    }
}
