package com.ohgiraffers.secondbackend.readingclub.client.dto;

public record ClubMemberLeaveMailRequest(
        String hostname,
        String clubName,
        String memberName
) {
}
