package com.example.news_feed.follow.controller;

import com.example.news_feed.follow.dto.FollowRequestDto;
import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.service.FollowService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Void> registerFollow(FollowRequestDto followRequestDto) {

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Follower(나를 팔로잉하는 사람들) 목록 조회
    @GetMapping
    public ResponseEntity<FollowResponseDto> findAll() {

        return new ResponseEntity<>( ,HttpStatus.OK);
    }

    // Following(내가 팔로잉 한 사람들) 목록 조회
    @GetMapping
    public ResponseEntity<FollowResponseDto> findAll() {
        FollowResponseDto followResponseDto = followservice.findAllFollowing();
        return new ResponseEntity<>(, HttpStatus.OK);
    }


    // Follow 취소


}
