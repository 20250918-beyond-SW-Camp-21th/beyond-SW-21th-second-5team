package com.ohgiraffers.secondbackend.readingclubreview.service;

import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClub;
import com.ohgiraffers.secondbackend.readingclub.entity.ReadingClubStatus;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubMemberRepository;
import com.ohgiraffers.secondbackend.readingclub.repository.ReadingClubRepository;
import com.ohgiraffers.secondbackend.readingclubreview.client.UserFeignClient;
import com.ohgiraffers.secondbackend.readingclubreview.dto.request.ReadingClubReviewRequestDTO;
import com.ohgiraffers.secondbackend.readingclubreview.dto.response.ReadingClubReviewResponseDTO;
import com.ohgiraffers.secondbackend.readingclubreview.entity.ReadingClubReview;
import com.ohgiraffers.secondbackend.readingclubreview.repository.ReadingClubReviewRepository;
import com.ohgiraffers.secondbackend.readingclubreview.service.ReadingClubReviewService;
import org.aspectj.util.Reflection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ReadingClubReviewServiceTest {


    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private ReadingClubReviewRepository reviewRepository;

    @Mock
    private ReadingClubRepository readingClubRepository;

    @Mock
    private ReadingClubMemberRepository memberRepository;

    @InjectMocks
    private ReadingClubReviewService reviewService;

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    void createReview_success() {
        // given
        Long clubId = 1L;
        Long userId = 100L;

        // 🔹 1) 클럽 엔티티 생성 (id는 빌더에 없음 → 나중에 리플렉션으로 주입)
        ReadingClub club = ReadingClub.builder()
                .name("테스트 모임")
                .description("설명")
                .userId(999L)
                .categoryId(3L)
                .status(ReadingClubStatus.OPEN)
                .build();

        // 👉 여기서만 강제로 private id 세팅
        ReflectionTestUtils.setField(club, "id", clubId);

        // 🔹 2) 요청 DTO 생성 (setter 없으면 이것도 ReflectionTestUtils로 세팅)
        ReadingClubReviewRequestDTO request = new ReadingClubReviewRequestDTO();
        ReflectionTestUtils.setField(request, "reviewTitle", "후기 제목");
        ReflectionTestUtils.setField(request, "reviewContent", "후기 내용");

        // 🔹 3) 리포지토리 동작 stubbing
        // 클럽 존재함
        given(readingClubRepository.findById(clubId))
                .willReturn(Optional.of(club));

        // 해당 모임의 멤버임
        given(memberRepository.existsByClubIdAndUserId(clubId, userId))
                .willReturn(true);

        // 아직 리뷰 안 썼음
        given(reviewRepository.existsByClubIdAndWriterId(club, userId))
                .willReturn(false);

        // 저장될 엔티티 가짜로 생성
        ReadingClubReview savedReview = ReadingClubReview.builder()
                .clubId(club)
                .writerId(userId)
                .reviewTitle("후기 제목")
                .reviewContent("후기 내용")
                .build();

        // review 엔티티에 id (reviewId) 필요하면 이렇게 세팅
        ReflectionTestUtils.setField(savedReview, "reviewId", 10L);

        given(reviewRepository.save(any(ReadingClubReview.class)))
                .willReturn(savedReview);

        // when
        ReadingClubReviewResponseDTO response =
                reviewService.createReview(clubId, request, userId);

        // then
        assertThat(response.getReviewTitle()).isEqualTo("후기 제목");
        assertThat(response.getReviewContent()).isEqualTo("후기 내용");
        // ResponseDTO에 reviewId 들어가있다면 이런 식으로도 검증 가능
        // assertThat(response.getReviewId()).isEqualTo(10L);

        // 호출 여부 검증
        verify(readingClubRepository).findById(clubId);
        verify(memberRepository).existsByClubIdAndUserId(clubId, userId);
        verify(reviewRepository).existsByClubIdAndWriterId(club, userId);
        verify(reviewRepository).save(any(ReadingClubReview.class));
    }

    @Test
    void modifyReview() {
        // given
        Long reviewId = 10L;
        Long userId = 100L;

        // 기존 리뷰 엔티티
        ReadingClub club = ReadingClub.builder()
                .name("모임")
                .description("설명")
                .userId(1L)
                .categoryId(1L)
                .status(ReadingClubStatus.OPEN)
                .build();
        ReflectionTestUtils.setField(club, "id", 1L);

        ReadingClubReview review = ReadingClubReview.builder()
                .clubId(club)
                .writerId(userId)
                .reviewTitle("옛날 제목")
                .reviewContent("옛날 내용")
                .build();
        ReflectionTestUtils.setField(review, "reviewId", reviewId);

        // 수정 요청 DTO
        ReadingClubReviewRequestDTO request = new ReadingClubReviewRequestDTO();
        ReflectionTestUtils.setField(request, "reviewTitle", "새 제목");
        ReflectionTestUtils.setField(request, "reviewContent", "새 내용");

        given(reviewRepository.findByReviewIdAndWriterId(reviewId, userId))
                .willReturn(Optional.of(review));

        // save 리턴은 보통 수정된 동일 엔티티라고 가정
        given(reviewRepository.save(any(ReadingClubReview.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ReadingClubReviewResponseDTO response =
                reviewService.modifyReview(reviewId, request, userId);

        // then
        assertThat(response.getReviewTitle()).isEqualTo("새 제목");
        assertThat(response.getReviewContent()).isEqualTo("새 내용");

        verify(reviewRepository).findByReviewIdAndWriterId(reviewId, userId);
        verify(reviewRepository).save(any(ReadingClubReview.class));
    }

    @Test
    void deleteReview() {
        // given
        Long userId = 100L;
        Long reviewId = 10L;
        // 기존 리뷰 엔티티
        ReadingClub club = ReadingClub.builder()
                .name("모임")
                .description("설명")
                .userId(100L)
                .categoryId(1L)
                .status(ReadingClubStatus.OPEN)
                .build();
        ReflectionTestUtils.setField(club, "id", 1L);

        ReadingClubReview review = ReadingClubReview.builder()
                .clubId(club)
                .writerId(userId)
                .reviewTitle("제목")
                .reviewContent("내용")
                .build();
        ReflectionTestUtils.setField(review,"reviewId", 1L);

        given(reviewRepository.findByReviewIdAndWriterId(reviewId,userId))
                .willReturn(Optional.of(review));

        // when
        reviewService.deleteReview(reviewId, userId);

        // then
        verify(reviewRepository).findByReviewIdAndWriterId(reviewId, userId);
        verify(reviewRepository).delete(any(ReadingClubReview.class));

    }

    @Test
    void getReviewsOrderByLatest() {
    }

    @Test
    void getReviewsOrderByLike() {
    }

    @Test
    void getMyReviews() {
    }
}