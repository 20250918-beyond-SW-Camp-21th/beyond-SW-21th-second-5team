package com.ohgiraffers.secondbackend.client.dto;

import java.util.List;

public record ClubDisbandMailRequest(
        List<String> memberEmails,
        String clubName
) {
}
