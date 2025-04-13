package com.example.news_feed.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PageResponseDto {
    private final List<BoardNewsFeedResponseDto> content;
    private final int totalPages;
    private final int currentPages;
}
