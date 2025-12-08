package com.ohgiraffers.secondbackend.users.dto.response;

import com.ohgiraffers.secondbackend.users.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String nickname;
    private UserRole role;
}

