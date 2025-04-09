package com.example.news_feed.follow.service;

import com.example.news_feed.follow.dto.FollowRequestDto;
import com.example.news_feed.follow.dto.FollowResponseDto;

public interface FollowService {
    public void registerFollow(FollowRequestDto followRequestDto);
    public void findAllFollower();
    public void findAllFollowing(FollowResponseDto followResponseDto);
}
