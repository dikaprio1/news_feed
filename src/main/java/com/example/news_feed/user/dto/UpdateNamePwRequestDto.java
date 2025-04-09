package com.example.news_feed.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateNamePwRequestDto {

    private final String username;

    @NotNull(message = "현재 비밀번호를 입력해주세요")
    private final String oldPassword;

    @NotNull(message = "변경할 비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상이어야 합니다")
    private final String newPassword;

}
