package com.example.news_feed.follow.controller;

import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.service.FollowService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;


    //변경 부분
    //팔로우 기능
    @PostMapping("/{id}")
    public ResponseEntity<String> followww(@Valid @PathVariable Long id, HttpSession session){
        FollowResponseDto followResponseDto = followService.followUser(id,session);
        return new ResponseEntity<>(followResponseDto.getFollowerUserName()+"님이 "+followResponseDto.getFollowingUserName()+"님을 팔로우 하였습니다.", HttpStatus.OK);
    }

    //팔로워 목록 조회
    @GetMapping("/followerList")
    public ResponseEntity<List<FollowerResponseDto>> findFollowerList(HttpSession session){
        List<FollowerResponseDto> followerList = followService.findFollowerList(session);
        return new ResponseEntity<>(followerList,HttpStatus.OK);
    }

    //팔로잉 목록 조회
    @GetMapping("/followingList")
    public ResponseEntity<List<FollowingResponseDto>> findFollowingList(HttpSession session){
        List<FollowingResponseDto> followingList = followService.findFollowingList(session);
        return new ResponseEntity<>(followingList,HttpStatus.OK);
    }

    //팔로우 취소 기능
    @DeleteMapping("/{id}")
    public ResponseEntity<String> unfollow(@PathVariable Long id, HttpSession session){
        FollowResponseDto followResponseDto = followService.unfollowUser(id,session);
        return new ResponseEntity<>(followResponseDto.getFollowerUserName()+"님이 "+followResponseDto.getFollowingUserName()+"님을 팔로우를 취소 하였습니다.", HttpStatus.OK);
    }
}
