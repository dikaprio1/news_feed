package com.example.news_feed.follow.repository;

import com.example.news_feed.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long>{
}
