package com.example.news_feed.follow.service;

import com.example.news_feed.auth.service.AuthServiceImpl;
import com.example.news_feed.exceptionhandler.AlreadyFollowerException;
import com.example.news_feed.exceptionhandler.FollowMySelfException;
import com.example.news_feed.exceptionhandler.FollowNotFoundException;
import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AuthServiceImpl authService;

    public FollowResponseDto followUser(Long id, HttpSession session) {
        User FindByIdUser = userRepository.findByIdOrElseThrow(id);
        User loginUser = authService.getLoginUser(session);
        if (loginUser.equals(FindByIdUser)) {
            throw new FollowMySelfException("자기 자신은 팔로우할 수 없습니다.");
        }
        boolean exists = followRepository.existsByFollowerIdAndFollowingId(loginUser, FindByIdUser);
        if (exists) {
            throw new AlreadyFollowerException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(loginUser, FindByIdUser);

        followRepository.save(follow);

        return new FollowResponseDto(follow.getFollowerId().getName(), follow.getFollowingId().getName());
    }

    public List<FollowerResponseDto> findFollowerList(HttpSession session) {
        User loginUser = authService.getLoginUser(session);
        List<Follow> findByUserIdFollower = followRepository.findByFollowerId(loginUser);
        if (findByUserIdFollower.isEmpty()) {
            throw new FollowNotFoundException("팔로워 정보가 없습니다.");
        }
        List<User> followers = followRepository.findFollowingsByFollowerId(loginUser.getId());
        return followers.stream()
                .map(user -> new FollowerResponseDto(user.getName()))
                .collect(Collectors.toList());


    }

    public List<FollowingResponseDto> findFollowingList(HttpSession session) {
        User loginUser = authService.getLoginUser(session);
        List<Follow> findByUserIdFollowing = followRepository.findByFollowingId(loginUser);
        if (findByUserIdFollowing.isEmpty()) {
            throw new FollowNotFoundException("팔로잉 정보가 없습니다.");
        }
        List<User> followings = followRepository.findFollowersByFollowingId(loginUser.getId());
        return followings.stream()
                .map(user -> new FollowingResponseDto(user.getName()))
                .collect(Collectors.toList());
    }
    @Transactional
    public FollowResponseDto unfollowUser(Long id, HttpSession session) {
        User loginUser = authService.getLoginUser(session);
        User FindByIdUser = userRepository.findByIdOrElseThrow(id);
        boolean exists = followRepository.existsByFollowerIdAndFollowingId(loginUser, FindByIdUser);
        if (!exists) {
            throw new IllegalStateException("팔로우 되어있지 않은 사용자입니다.");
        }
        followRepository.deleteByFollowerIdAndFollowingId(loginUser, FindByIdUser);
        return new FollowResponseDto(loginUser.getName(),FindByIdUser.getName());
    }
}
