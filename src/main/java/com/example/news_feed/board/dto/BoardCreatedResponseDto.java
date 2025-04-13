package com.example.news_feed.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BoardCreatedResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String imageUrl;
    private final String username;
    private final LocalDateTime createdAt;
}
