package com.example.news_feed.follow.service;

import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowResponseDto followUser(Long id, HttpSession session){
        User findBySessionUser = userRepository.findByEmailOrElseThrow((String) session.getAttribute("user"));
        User findByIdUser = userRepository.findByIdOrElseThrow(id);
        Follow follow = new Follow();
        follow.setFollower(findBySessionUser);
        follow.setFollowing(findByIdUser);

        followRepository.save(follow);

        return new FollowResponseDto(follow.getFollower(),follow.getFollowing());
    }
}
