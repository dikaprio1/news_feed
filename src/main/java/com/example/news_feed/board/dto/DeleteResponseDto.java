package com.example.news_feed.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class DeleteResponseDto {
    private final Long id;
    private final LocalDateTime deletedAt;
    private final String message;
}
