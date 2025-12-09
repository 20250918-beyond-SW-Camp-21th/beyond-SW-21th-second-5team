package com.ohgiraffers.secondbackend.bookreport.dto.response;

import com.ohgiraffers.secondbackend.bookreport.entity.BookReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class BookReportResponseDTO {
    private String bookTittle;    //책 제목
    private String title;   //독후감 제목
    private String description;
    private int likeCount;
    private LocalDateTime createdAt;
    private String username;
    private String nickname;

}
