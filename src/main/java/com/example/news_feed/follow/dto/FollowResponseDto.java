package com.example.news_feed.follow.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowResponseDto {
    @NotNull
    private final String email;

    private final Long Follower;

    private final Long Following;

}
