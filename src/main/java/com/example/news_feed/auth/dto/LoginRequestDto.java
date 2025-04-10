package com.example.news_feed.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 로그인 요청

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    @NotNull
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private final String email;

    @NotNull
    private final String password;

}