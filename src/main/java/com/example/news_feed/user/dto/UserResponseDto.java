package com.example.news_feed.user.dto;

import common.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 프로필 조회 응답

@Getter
@RequiredArgsConstructor
public class UserResponseDto {

    private final String username;
    private final String email;
    private final Gender gender;
    private final Integer age;

}
