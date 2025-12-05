package com.ohgiraffers.secondbackend.readingclub.dto.response;

import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClubStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReadingClubResponseDTO {
    private int id;
    private String name;
    private String description;
    private ReadingClubStatus status;
    private LocalDateTime createdAt;
    private int hostUserId;
    private int categoryId;
}
