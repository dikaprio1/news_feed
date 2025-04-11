package com.example.news_feed.board.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class BoardRequestDto {

    private final String content;
    @NotBlank(message = "제목은 필수입니다")
    private final String title;
    private final String imageUrl;
}

