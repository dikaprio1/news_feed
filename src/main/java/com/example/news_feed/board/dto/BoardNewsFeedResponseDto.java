package com.example.news_feed.board.dto;

import com.example.news_feed.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import com.example.news_feed.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;


    @Getter
    @RequiredArgsConstructor
    public class BoardNewsFeedResponseDto {

        @NotNull
        private final Long id;
        @NotNull(message = "제목은 필수입니다")
        private final String title;
        @NotBlank
        private final String content;

        private final String imageUrl;
        @NotNull
        private final String username;


        // 생성일 추가를 위해 responsedto 새로 생성
        private final LocalDateTime createAt;

        private final LocalDateTime modifiedAt;

}
