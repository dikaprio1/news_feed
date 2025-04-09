package com.example.news_feed.follow.repository;

import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long>{


    Boolean existsByFollowerIdAndFollowingId(Long myId, Long followId);

    List<FollowingResponseDto> findByFollowerId();

    List<FollowerResponseDto> findByFollwingId();
}
