package com.ohgiraffers.secondbackend.readingclubreview.service;

import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubMemberRepository;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubRepository;
import com.ohgiraffers.secondbackend.readingclubreview.dto.request.ReadingClubReviewRequestDTO;
import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReadingClubReviewResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import com.ohgiraffers.secondbackend.readingclubreview.repository.ReadingClubReviewRepository;
import com.ohgiraffers.secondbackend.user.entity.User;
import com.ohgiraffers.secondbackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadingClubReviewService {

    private final UserRepository userRepository;
    private final ReadingClubReviewRepository reviewRepository;
    private final ReadingClubRepository readingClubRepository;
    private final ReadingClubMemberRepository memberRepository;

    @Transactional
    public ReadingClubReviewResponseDTO createReview(long clubId, ReadingClubReviewRequestDTO request, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다."));

        Long userId = user.getId();
        // 1) 클럽 존재 여부 체크
        readingClubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 클럽입니다."));

        // 2) 클럽 회원인지 확인
        boolean isMember = memberRepository.existsByClubIdAndUserId(clubId, userId);
        if (!isMember) {
            log.warn("유저 {} 는 클럽 {} 멤버가 아님", userId, clubId);
            throw new AccessDeniedException("해당 모임 참가자만 후기를 작성할 수 있습니다.");
        }

        // 3) 이미 작성한 후기 있는지 체크
        boolean alreadyWritten = reviewRepository.existsByClubIdAndWriterId(clubId, userId);
        if (alreadyWritten) {
            throw new IllegalStateException("이 모임에 이미 후기를 작성하셨습니다.");
        }

        ReadingClubReview review = ReadingClubReview.builder()
                .clubId(clubId)
                .writerId(user.getId())
                .reviewTitle(request.getReviewTitle())
                .reviewContent(request.getReviewContent())
                .build();

        ReadingClubReview saved = reviewRepository.save(review);

        return ReadingClubReviewResponseDTO.from(saved);
    }
}
