package com.example.news_feed.follow.controller;

import com.example.news_feed.follow.dto.FollowRequestDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //
@RequestMapping("/api/follow") // API 매핑 정하기
@RequiredArgsConstructor // 생성자 자동 생성
public class FollowController {

    private final FollowService followservice;

    // Following 하기
    @PostMapping("/request")
    public ResponseEntity<Void> registerFollow(@RequestBody FollowRequestDto followRequestDto, @SessionAttribute(name = "user") String loginEmail) {
        followservice.registerFollow(followRequestDto, loginEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // Follower(나를 팔로잉하는 사람들) 목록 조회
    @GetMapping("/followers")
    public ResponseEntity<List<FollowerResponseDto>> findFollowers(@SessionAttribute(name = "user") String loginEmail) {
        List<FollowerResponseDto> followerResponseDto = followservice.findFollowers(loginEmail);
        return new ResponseEntity<>(followerResponseDto ,HttpStatus.OK);
    }

    // Following(내가 팔로잉 한 사람들) 목록 조회
    @GetMapping("/followings")
    public ResponseEntity<List<FollowingResponseDto>> findFollowings(@SessionAttribute(name = "user") String loginEmail) {
        List<FollowingResponseDto> followingResponseDto = followservice.findFollowings(loginEmail);
        return new ResponseEntity<>(followingResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteFollow(@RequestBody FollowRequestDto followRequestDto, @SessionAttribute(name = "user") String loginEmail) {
        followservice.deleteFollow(followRequestDto, loginEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // followResponsedto 만들어놓기
    // 팔로우에 대한 응답을 만들어놓고 언젠간 쓸 수도 있다..

}
