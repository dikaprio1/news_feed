package com.example.news_feed.follow.service;

import com.example.news_feed.follow.dto.FollowRequestDto;
import com.example.news_feed.follow.dto.FollowResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface FollowService {
    public void registerFollow(FollowRequestDto followRequestDto, HttpServletRequest request);


    /*
    public void findAllFollower();
    public void findAllFollowing(FollowResponseDto followResponseDto);

     */
}
