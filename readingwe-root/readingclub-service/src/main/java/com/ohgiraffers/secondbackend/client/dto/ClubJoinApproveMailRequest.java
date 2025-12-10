package com.ohgiraffers.secondbackend.client.dto;

public record ClubJoinApproveMailRequest(
        String email,
        String clubName
) {
}
