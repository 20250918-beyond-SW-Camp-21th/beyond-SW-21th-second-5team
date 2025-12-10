package com.ohgiraffers.bookservice.secondbackend.bookreport.controller;

import com.ohgiraffers.bookservice.secondbackend.bookreport.dto.request.BookReportCommentRequestDTO;
import com.ohgiraffers.bookservice.secondbackend.bookreport.dto.response.BookReportCommentResponseDTO;
import com.ohgiraffers.bookservice.secondbackend.bookreport.service.BookReportCommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-report-comment")
@RequiredArgsConstructor
public class BookReportCommentController {

    private final BookReportCommentService bookReportCommentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<BookReportCommentResponseDTO> createComment(
            @RequestBody BookReportCommentRequestDTO request,
            HttpServletRequest req) {

        String rawUserId = req.getHeader("X-User-ID");
        if (rawUserId == null || rawUserId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId;

        try {
            userId = Long.parseLong(rawUserId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        BookReportCommentResponseDTO response =
                bookReportCommentService.saveBookReportComment(request,userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<BookReportCommentResponseDTO> updateComment(
            @RequestBody BookReportCommentRequestDTO request,
            @PathVariable Long commentId){

        BookReportCommentResponseDTO response = bookReportCommentService.changeBookComment(commentId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId){
        bookReportCommentService.deleteBookComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 댓글이 삭제됩니다.");
    }

    // 댓글 전체 조회
    @GetMapping("/{bookReportId}/comments")
    public ResponseEntity<List<BookReportCommentResponseDTO>> getComments(
            @PathVariable Long reportId){
        List<BookReportCommentResponseDTO> comments =
                bookReportCommentService.getCommentsByReportId(reportId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

}
