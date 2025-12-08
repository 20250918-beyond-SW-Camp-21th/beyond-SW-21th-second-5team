package com.ohgiraffers.emailservice.dto;

public record SignupVerificationMailRequest(
        String email,
        String username,
        String verificationCode
) {}
