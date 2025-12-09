package com.ohgiraffers.secondbackend.readingclub.client.dto;

public record ClubJoinRejectMailRequest(
        String email,
        String clubName,
        String reason
) {
}
