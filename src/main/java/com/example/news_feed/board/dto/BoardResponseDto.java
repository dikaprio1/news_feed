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
    public class BoardResponseDto {

        @NotNull
        private final Long id;
        @NotNull(message = "제목은 필수입니다")
        private final String title;
        @NotBlank
        private final String content;

        private final String imageUrl;
        @NotNull
        private final String username;


//         전체필드를 초기화하는 생성자
//         모든필드를 매개변수로 받아서 초기화하는 생성자
//         final필드는 생성자에서 무조건 초기화해줘야해서 필요함
//         이 생성자를 통해 외부에서도 DTO를 만들수있음
//        public BoardResponseDto(Long id, String title, String content, String imageUrl,
//                                LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
//            this.id = id;
//            this.title = title;
//            this.content = content;
//            this.imageUrl = imageUrl;
//            this.createdAt = createdAt;
//            this.modifiedAt = modifiedAt;
//            this.deletedAt = deletedAt;
//        }


//         Board 엔티티로부터 바로 DTO 생성하는 생성자
//         Board객체를 한번에 받아서 DTO로 변환해주는 편의생성자
//         내부에서 전체 생성자를 호출함
//        public BoardResponseDto(Board board) {
//            this(
//                    board.getId(),
//                    board.getTitle(),
//                    board.getContent(),
//                    board.getImageUrl(),
//                    board.getCreatedAt(),
//                    board.getModifiedAt(),
//                    board.getDeletedAt()
//            );
//        }

    }


