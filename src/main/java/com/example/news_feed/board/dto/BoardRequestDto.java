package com.example.news_feed.board.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class BoardRequestDto {

    private final String title;
    private final String content;
    private final String imageUrl;
}

