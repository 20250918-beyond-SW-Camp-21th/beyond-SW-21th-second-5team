package com.ohgiraffers.secondbackend.readingclub.client.dto;

import java.util.List;

public record ClubJoinRequestMailRequest(
        String hostEmail,
        String clubName,
        String applicantName
) {
}
