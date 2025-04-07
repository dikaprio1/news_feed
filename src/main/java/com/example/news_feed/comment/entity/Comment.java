package com.example.news_feed.comment.entity;

import baseentity.BaseEntity;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
    // 기본 키 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    // 외래키 참조(user_id)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 외래키 참조(board_id)
    @ManyToOne
    @JoinColumn(name ="board_id")
    private Board board;

}
