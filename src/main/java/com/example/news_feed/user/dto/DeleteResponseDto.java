package com.example.news_feed.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class DeleteResponseDto {

    private final LocalDateTime deletedAt;

    private final String message;

}
