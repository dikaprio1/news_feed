package com.example.news_feed.follow.repository;

import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>{


    Boolean existsByFollowerIdAndFollowingId(User followerId, User followingId);


    List<Follow> findByFollowerId(User followerId);
    List<Follow> findByFollowingId(User followingId);

    Optional<Follow> findByFollowerIdAndFollowingId(User followerId, User followingId);
}
