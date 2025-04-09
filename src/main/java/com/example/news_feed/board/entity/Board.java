package com.example.news_feed.board.entity;


import com.example.news_feed.baseentity.BaseEntity;
import com.example.news_feed.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "board")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    public Board(){

    }

    public Board(String title, String content,String image){
        this.title = title;
        this.content = content;
        this.image = image;
    }

    @Column(nullable = false)
    private String content;
    private String image;

    //유저 참조
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}





