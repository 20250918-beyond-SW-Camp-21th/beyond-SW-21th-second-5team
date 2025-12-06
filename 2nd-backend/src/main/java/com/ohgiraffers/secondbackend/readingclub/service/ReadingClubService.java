package com.ohgiraffers.secondbackend.readingclub.service;

import com.ohgiraffers.secondbackend.readingclub.dto.request.ReadingClubRequestDTO;
import com.ohgiraffers.secondbackend.readingclub.dto.response.ReadingClubResponseDTO;
import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClub;
import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClubStatus;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubRepository;
import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReadingClubService {

    private final ReadingClubRepository readingClubRepository;
    private final UserRepository userRepository;

    private ReadingClubResponseDTO convert(ReadingClub club) {
        return ReadingClubResponseDTO.builder()
                .id(club.getId())
                .name(club.getName())
                .description(club.getDescription())
                .status(club.getStatus())
                .createdAt(club.getCreatedAt())
                .hostUserId(club.getUserId())
                .categoryId(club.getCategoryId())
                .build();
    }


    @Transactional
    public ReadingClubResponseDTO createReadingClub(ReadingClubRequestDTO req, long hostId) {       // 클럽 생성
        ReadingClub rc = ReadingClub.builder()
                .name(req.getName())
                .description(req.getDescription())
                .userId(hostId)
                .categoryId(req.getCategoryId())
                .status(ReadingClubStatus.OPEN)
                .build();

        ReadingClub saved = readingClubRepository.save(rc);
        return convert(saved);
    }

    @Transactional
    public ReadingClubResponseDTO updateReadingClub(Long clubId, ReadingClubRequestDTO req, long hostId) {
        ReadingClub club = readingClubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));

        if (club.getUserId() != hostId) {
            throw new SecurityException("모임을 삭제할 권한이 없습니다.");
        }

        club.update(req.getName(), req.getDescription(), req.getCategoryId());
        return convert(club);
    }

    @Transactional
    public void deleteReadingClub(Long clubId, long hostId){
        ReadingClub club = readingClubRepository.findById(clubId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 모임입니다."));

        if (club.getUserId() != hostId){
            throw new SecurityException("모임을 삭제할 권한이 없습니다.");
        }
        club.finish();
    }
}
