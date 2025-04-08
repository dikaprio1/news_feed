package com.example.news_feed.follow.service;

import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    // Following 하기


    // Follower(나를 팔로잉하는 사람들) 목록 조회

    // Following(내가 팔로잉 한 사람들) 목록 조회


    // Follow 취소



    // 나중에 친구 게시물 보기 등의 기능 고려...
    // private final BoardRepository boardRepository;



}
