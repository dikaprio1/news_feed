package com.example.news_feed.auth.dto;

import com.example.news_feed.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 회원가입 요청

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private final String email;

    @NotBlank
    private final String password;

    @NotNull
    private final Gender gender;

    @NotNull
    private final Integer age;

}
