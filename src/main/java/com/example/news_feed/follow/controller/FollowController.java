package com.example.news_feed.follow.controller;

import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.service.FollowService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{id}")
    public ResponseEntity<FollowResponseDto> followUser(@Valid @PathVariable Long id, HttpSession session){
        FollowResponseDto followResponseDto = followService.followUser(id,session);
        return new ResponseEntity<>(followResponseDto, HttpStatus.OK);
    }
}
