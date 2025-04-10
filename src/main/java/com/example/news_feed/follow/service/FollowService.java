package com.example.news_feed.follow.service;

import com.example.news_feed.exceptionhandler.UserNotFoundException;
import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowResponseDto followUser(Long id, HttpSession session){
        Optional<User> findBySessionUser = userRepository.findByEmail((String) session.getAttribute("user"));
        Optional<User> findByIdUser = userRepository.findById(id);
        if(findBySessionUser.isEmpty()){
            throw new UserNotFoundException("로그인이 필요합니다.");
        }
        if(findByIdUser.isEmpty()){
            throw new UserNotFoundException("팔로우 대상을 찾을 수 없습니다.");
        }
        Follow follow = new Follow();
        follow.setFollower(findBySessionUser.get());
        follow.setFollowing(findByIdUser.get());

        followRepository.save(follow);

        return new FollowResponseDto(follow.getFollower().getName(),follow.getFollowing().getName());
    }
}
