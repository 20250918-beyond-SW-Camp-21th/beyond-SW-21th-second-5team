package com.ohgiraffers.secondbackend.readingclub.controller;

import com.ohgiraffers.secondbackend.readingclub.dto.request.ReadingClubRequestDTO;
import com.ohgiraffers.secondbackend.readingclub.dto.response.ReadingClubResponseDTO;
import com.ohgiraffers.secondbackend.readingclub.service.ReadingClubService;
import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import com.ohgiraffers.secondbackend.user.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reading-club")
@RequiredArgsConstructor
public class ReadingClubController {

    private final ReadingClubService readingClubService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping ("/club-create")       // JWT 구현시 @RequestParam 파트 빼고 수정 완료
    public ResponseEntity<ReadingClubResponseDTO> createReadingClub(@RequestBody ReadingClubRequestDTO req
            , Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        long hostId = user.getId();
        ReadingClubResponseDTO res = readingClubService.createReadingClub(req, hostId);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PatchMapping
    public ResponseEntity<ReadingClubResponseDTO> updateReadingClub(@PathVariable long clubId, @RequestBody ReadingClubRequestDTO req
                                                                     , Authentication authentication) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        long hostId = user.getId();

        ReadingClubResponseDTO res = readingClubService.updateReadingClub(clubId, req, hostId);
        return ResponseEntity.ok(res);
    }
}
