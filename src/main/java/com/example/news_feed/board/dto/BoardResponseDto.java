package com.example.news_feed.board.dto;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;



    public BoardResponseDto(Long id, String title, String author,String content, String imageUrl,LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt ){
        this.id= id;
        this.title=title;
        this.author=author;
        this.content=content;
        this.imageUrl=imageUrl;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.deletedAt=deletedAt;
    }
}
