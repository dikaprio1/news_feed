package com.example.news_feed.board.dto;


import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BoardResponseDto {

    @NotNull(message = "제목은 필수입니다")
    private final String title;
    @NotNull
    private final String author;

    private final String content;
    private final String image;

    @CreatedDate
    private final LocalDateTime createdAt;
}
