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
        Optional<User> optionalFindBySessionUser = userRepository.findByEmail((String) session.getAttribute("user"));
        Optional<User> optionalFindByIdUser = userRepository.findById(id);
        if(optionalFindBySessionUser.isEmpty()){
            throw new UserNotFoundException("로그인 정보를 불러올 수 업습니다. 로그인이 필요합니다.");
        }
        if(optionalFindByIdUser.isEmpty()){
            throw new UserNotFoundException("팔로우 대상을 찾을 수 없습니다.");
        }
        User findBySessionUser = optionalFindBySessionUser.get();
        User findByIdUser = optionalFindByIdUser.get();
        if (findBySessionUser.equals(findByIdUser)) {
            throw new IllegalArgumentException("자기 자신은 팔로우할 수 없습니다.");
        }
        boolean exists = followRepository.existsByFollowerIdAndFollowingId(findBySessionUser.getId(), findByIdUser.getId());
        if (exists) {
            throw new IllegalStateException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(findBySessionUser,findByIdUser);

        followRepository.save(follow);

        return new FollowResponseDto(follow.getFollower().getName(),follow.getFollowing().getName());
    }
}
