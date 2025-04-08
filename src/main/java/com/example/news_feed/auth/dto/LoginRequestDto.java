package com.example.news_feed.auth.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 로그인 요청

@Getter
@RequiredArgsConstructor
public class LoginRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
            message = "이메일 형식이 올바르지 않습니다")
    private final String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상이어야 합니다")
    private final String password;

}
