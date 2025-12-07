package com.ohgiraffers.secondbackend.bookreport.controller;

import com.ohgiraffers.secondbackend.bookreport.dto.request.BookReportRequestDTO;
import com.ohgiraffers.secondbackend.bookreport.dto.response.BookReportResponseDTO;
import com.ohgiraffers.secondbackend.bookreport.entity.BookReport;
import com.ohgiraffers.secondbackend.bookreport.service.BookReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-report")
@RequiredArgsConstructor
public class BookReportController {

    private final BookReportService bookReportService;

    //독후감 등록
    @PostMapping()
    public ResponseEntity<BookReportResponseDTO> createBookReport(
            @RequestBody BookReportRequestDTO request){

        BookReportResponseDTO response = bookReportService.saveBookReport(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
