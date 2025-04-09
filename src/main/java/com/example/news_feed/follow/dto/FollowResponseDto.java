package com.example.news_feed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FollowResponseDto {
    // 조회할 유저의 email
    @NotNull
    private final String email;


    // 응답 받아올 목록이기때문에 List로 담아오기
    private final List<String> followings;

}
