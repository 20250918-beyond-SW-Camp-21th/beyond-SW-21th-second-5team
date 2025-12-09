package com.ohgiraffers.secondbackend.readingclub.client.dto;

public record ClubJoinApproveMailRequest(
        String email,
        String clubName
) {
}
