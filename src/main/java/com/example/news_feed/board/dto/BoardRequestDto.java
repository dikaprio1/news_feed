package com.example.news_feed.board.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    private long Id;
    private String author;
    private String content;
    private String image;

    @NotNull(message = "제목은 필수입니다")
    private String title;

}
