package com.example.news_feed.follow.repository;

import com.example.news_feed.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>{

    // Following 하기
    // 본인, 이미 팔로우 한 친구에 대한 예외처리
}
