package com.example.news_feed.board.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateBoardRequestDto {
    private String title;
    private String content;
    private String imageUrl;
}
