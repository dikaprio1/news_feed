package com.example.news_feed.board.entity;

import com.example.news_feed.baseentity.BaseEntity;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String content;

    private String imageUrl;

    //유저 참조
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //게시글 수정시 사용할 메서드
    //BoardRequestDto에서 받은값으로 내부필드(title,content)를 업데이트함
    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.imageUrl = boardRequestDto.getImageUrl();
    }

    public Board(){
    }

    public Board(String title, String content,String imageUrl){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }
    public Board(String title, String content,String imageUrl,User user){
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}







