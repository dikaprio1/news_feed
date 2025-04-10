package com.example.news_feed.follow.entity;

import com.example.news_feed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 지연 로딩 개념 적용 (fetch = FetchType.LAZY) 일단 보류
    @ManyToOne
    @JoinColumn(referencedColumnName ="user_id", name = "follower", nullable = false)
    // 한 명의 유저를 여러명이 팔로우 할 수 있다....
    private User followerId;

    // 지연 로딩 개념 적용 (fetch = FetchType.LAZY) 일단 보류
    @ManyToOne
    @JoinColumn(referencedColumnName ="user_id", name = "following", nullable = false)
    // 한 명의 유저가 여러 아이디를 팔로우할 수 있다?
    private User followingId;

    private LocalDateTime createdAt;

    // createdAt을 생성자에 now로 선언해줌으로써 생성시간을 표현해줌
    // 자료형 User로 받아오기
    public Follow(User followerId, User followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.createdAt = LocalDateTime.now();
    }

    public Follow() {
    }
}
