package com.example.news_feed.follow.service;

import com.example.news_feed.follow.dto.FollowRequestDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;

import java.util.List;

public interface FollowService {
    public void registerFollow(FollowRequestDto followRequestDto, String loginEmail);

    public void deleteFollow(FollowRequestDto followRequestDto, String loginEmail);

    public List<FollowerResponseDto> findFollowers(String loginEmail);

    public List<FollowingResponseDto> findFollowings(String loginEmail);
}
