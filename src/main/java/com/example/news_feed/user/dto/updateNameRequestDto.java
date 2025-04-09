package com.example.news_feed.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 프로필 - 이름 수정 요청

@Getter
@RequiredArgsConstructor
public class updateNameRequestDto {

    @NotNull(message = "변경할 이름을 입력해주세요")
    private final String username;

    @NotNull(message = "현재 비밀번호를 입력해주세요")
    private final String oldPassword;

}
