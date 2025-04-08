package com.example.news_feed.follow.controller;

import com.example.news_feed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //
@RequestMapping("/follow") // API 매핑 정하기
@RequiredArgsConstructor // 생성자 자동 생성
public class FollowController {

    private final FollowService followservice;


    // Following 하기 (send?)
    @PostMapping("/follow/request")
    public ResponseEntity<> registerFollow() {

    }


    // Follower(나를 팔로잉하는 사람들) 목록 조회

    // Following(내가 팔로잉 한 사람들) 목록 조회


    // Follow 취소


}
