package com.example.news_feed.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.crypto.Data;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

    private String title;
    private String author;
    private String content;
    private String image;
    private Data created_at;






}
