package com.example.news_feed.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeletePostRequestDto {

    @Email(message = "이메일 형식이 올바르지 않습니다")
    private final String email;

    private final String password;
}

