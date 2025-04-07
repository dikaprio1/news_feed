package com.example.news_feed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    // 한 명의 유저를 여러명이 팔로우 할 수 있다....
    private User followerId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    // 한 명의 유저가 여러 아이디를 팔로우할 수 있다?
    private User followingId;


    //


    // 요청 request

    //

}