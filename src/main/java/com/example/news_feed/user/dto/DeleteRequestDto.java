package com.example.news_feed.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//　회원 탈퇴 요청 -> 이메일 비번

@Getter
@RequiredArgsConstructor
public class DeleteRequestDto {

    @NotNull(message = "이메일을 입력해주세요")
    private final String email;

    @NotNull(message = "비밀번호를 입력해주세요")
    private final String password;

}
