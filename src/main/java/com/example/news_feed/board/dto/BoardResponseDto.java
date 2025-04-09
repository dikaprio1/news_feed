package com.example.news_feed.board.dto;


import com.example.news_feed.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import java.time.LocalDateTime;

    @Getter
    public class BoardResponseDto {

        @NotNull
        private final Long id;
        @NotBlank
        private final String title;
        @NotBlank
        private final String content;
        private final String imageUrl;

        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;
        private final LocalDateTime deletedAt;



        public BoardResponseDto(Long id, String title, String content, String imageUrl,
                                LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.imageUrl = imageUrl;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.deletedAt = deletedAt;
        }




        public BoardResponseDto(Board board) {
            this(
                    board.getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getImageUrl(),
                    board.getCreatedAt(),
                    board.getUpdatedAt(),
                    board.getDeletedAt()
            );
        }

    }