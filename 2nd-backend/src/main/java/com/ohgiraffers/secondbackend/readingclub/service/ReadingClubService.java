package com.ohgiraffers.secondbackend.readingclub.service;

import com.ohgiraffers.secondbackend.readingclub.dto.request.ReadingClubRequestDTO;
import com.ohgiraffers.secondbackend.readingclub.dto.response.ReadingClubResponseDTO;
import com.ohgiraffers.secondbackend.readingclub.entity.JoinRequestStatus;
import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClub;
import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClubJoin;
import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClubStatus;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubJoinRepository;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubMemberRepository;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubRepository;
import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadingClubService {

    private final ReadingClubRepository readingClubRepository;
    private final ReadingClubMemberRepository readingClubMemberRepository;
    private final ReadingClubJoinRepository readingClubJoinRepository;
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


    @Transactional      // 모임 생성
    public ReadingClubResponseDTO createReadingClub(ReadingClubRequestDTO req, long hostId) {
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

    @Transactional      // 모임 수정
    public ReadingClubResponseDTO updateReadingClub(Long clubId, ReadingClubRequestDTO req, long hostId) {
        ReadingClub club = readingClubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));

        if (club.getUserId() != hostId) {
            throw new SecurityException("모임을 수정할 권한이 없습니다.");
        }

        club.update(req.getName(), req.getDescription(), req.getCategoryId());
        return convert(club);
    }

    @Transactional      // 모임 수정
    public void deleteReadingClub(Long clubId, long hostId){
        ReadingClub club = readingClubRepository.findById(clubId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 모임입니다."));

        if (club.getUserId() != hostId){
            throw new SecurityException("모임을 삭제할 권한이 없습니다.");
        }
        club.finish();
    }

    @Transactional
    public void requestJoin(Long clubId, Long userId, String message){
        ReadingClub club = readingClubRepository.findById(clubId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모임입니다."));

        if(readingClubMemberRepository.existsByClubIdAndUserId(clubId, userId)){
            throw new IllegalStateException("이미 해당 모임에 가입했습니다.");
        }

        boolean existRequest = readingClubJoinRepository.existsByClubIdAndUserIdAndStatusIn(clubId, userId, List.of(JoinRequestStatus.PENDING));

        if(existRequest){
            throw new IllegalStateException("이미 신청했습니다.");
        }

        ReadingClubJoin request = ReadingClubJoin.builder()
                .clubId(clubId)
                .userId(userId)
                .message(message)
                .status(JoinRequestStatus.PENDING)
                .build();

        readingClubJoinRepository.save(request);
    }
}
