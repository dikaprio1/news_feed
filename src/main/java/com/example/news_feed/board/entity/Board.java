package com.example.news_feed.board.entity;


import com.example.news_feed.baseentity.BaseEntity;
import com.example.news_feed.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "board")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
    private String image;
    private String author;
    private LocalDateTime createdAt;

    //유저 참조
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}





