package com.example.news_feed.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDeleteResponseDto {
    private Long id;
    private LocalDateTime deletedAt;
    private String message;
}
