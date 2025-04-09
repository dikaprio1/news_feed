package com.example.news_feed.board.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    @NotNull(message = "제목은 필수입니다")
    private String title;

    private String content;

    private String image;

}
