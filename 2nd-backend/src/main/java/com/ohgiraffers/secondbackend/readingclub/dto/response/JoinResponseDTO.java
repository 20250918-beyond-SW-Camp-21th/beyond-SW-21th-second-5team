package com.ohgiraffers.secondbackend.readingclub.dto.response;

import com.ohgiraffers.secondbackend.readingclub.entity.JoinRequestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class JoinResponseDTO {

    private Long id;
    private Long clubId;
    private Long userId;
    private String message;
    private JoinRequestStatus status;
    private LocalDateTime createdAt;
}
