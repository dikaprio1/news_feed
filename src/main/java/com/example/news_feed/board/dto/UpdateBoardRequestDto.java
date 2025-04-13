package com.example.news_feed.board.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateBoardRequestDto {
    private final String title;
    private final String content;
    private final String imageUrl;
}
