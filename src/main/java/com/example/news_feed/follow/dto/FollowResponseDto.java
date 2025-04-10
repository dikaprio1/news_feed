package com.example.news_feed.follow.dto;

import com.example.news_feed.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowResponseDto {

    @NotNull
    private final String followerUserName;
    @NotNull
    private final String followingUserName;
}
