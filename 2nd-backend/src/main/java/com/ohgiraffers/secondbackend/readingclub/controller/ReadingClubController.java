package com.ohgiraffers.secondbackend.readingclub.controller;

import com.ohgiraffers.secondbackend.readingclub.dto.request.ReadingClubRequestDTO;
import com.ohgiraffers.secondbackend.readingclub.dto.response.ReadingClubResponseDTO;
import com.ohgiraffers.secondbackend.readingclub.service.ReadingClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reading-club")
@RequiredArgsConstructor
public class ReadingClubController {

    private final ReadingClubService readingClubService;

    @PostMapping        // JWT 구현시 @RequestParam 파트 빼고 수정 예정
    public ResponseEntity<ReadingClubResponseDTO> createReadingClub(@RequestBody ReadingClubRequestDTO req, @RequestParam int hostId) {
        ReadingClubResponseDTO res = readingClubService.createReadingClub(req, hostId);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);         // 201 반환
    }
}
