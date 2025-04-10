package com.example.news_feed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowerResponseDto {
    @NotNull
    private final String followerUserName;

}
