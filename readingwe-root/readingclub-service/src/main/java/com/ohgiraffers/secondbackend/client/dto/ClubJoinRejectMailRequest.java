package com.ohgiraffers.secondbackend.client.dto;

public record ClubJoinRejectMailRequest(
        String email,
        String clubName,
        String reason
) {
}
