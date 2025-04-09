package com.example.news_feed.board.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class BoardRequestDto {
    private final String content;
    private final String image;

    @NotNull(message = "제목은 필수입니다")
    private final String title;

}
