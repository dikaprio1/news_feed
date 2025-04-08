package com.example.news_feed.auth.dto;

import common.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.Pattern;

// 회원가입 요청

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "이메일 형식이 올바르지 않습니다")
    private final String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상이어야 합니다") // 실패시 400 (Valid가 자동 응답)
    private final String password;

    @NotNull
    private final Gender gender;

    @NotNull
    private final Integer age;

}
