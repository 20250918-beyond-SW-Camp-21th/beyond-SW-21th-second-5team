package com.ohgiraffers.secondbackend.client.dto;

public record ClubMemberLeaveMailRequest(
        String hostname,
        String clubName,
        String memberName
) {
}
