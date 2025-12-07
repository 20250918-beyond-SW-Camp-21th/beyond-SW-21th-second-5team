package com.ohgiraffers.secondbackend.bookreport.controller;

import com.ohgiraffers.secondbackend.bookreport.dto.request.BookReportCommentRequestDTO;
import com.ohgiraffers.secondbackend.bookreport.dto.request.BookReportRequestDTO;
import com.ohgiraffers.secondbackend.bookreport.dto.response.BookReportCommentResponseDTO;
import com.ohgiraffers.secondbackend.bookreport.service.BookReportCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-report-comment")
@RequiredArgsConstructor
public class BookReportCommentController {

    private final BookReportCommentService bookReportCommentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<BookReportCommentResponseDTO> createComment(
            @RequestBody BookReportCommentRequestDTO request){

        BookReportCommentResponseDTO response = bookReportCommentService.saveBookReportComment(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
