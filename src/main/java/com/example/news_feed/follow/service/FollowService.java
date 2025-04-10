package com.example.news_feed.follow.service;

import com.example.news_feed.auth.service.AuthServiceImpl;
import com.example.news_feed.exceptionhandler.FollowerNotFoundException;
import com.example.news_feed.exceptionhandler.FollowingNotFoundException;
import com.example.news_feed.exceptionhandler.UserNotFoundException;
import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AuthServiceImpl authService;

    public FollowResponseDto followUser(Long id, HttpSession session) {
        Optional<User> optionalFindByIdUser = userRepository.findById(id);
        if (optionalFindByIdUser.isEmpty()) {
            throw new UserNotFoundException("팔로우 대상을 찾을 수 없습니다.");
        }
        User loginUser = authService.getLoginUser(session);
        User findByIdUser = optionalFindByIdUser.get();
        if (loginUser.equals(findByIdUser)) {
            throw new IllegalArgumentException("자기 자신은 팔로우할 수 없습니다.");
        }
        boolean exists = followRepository.existsByFollowerIdAndFollowingId(loginUser, findByIdUser);
        if (exists) {
            throw new IllegalStateException("이미 팔로우한 사용자입니다.");
        }

        Follow follow = new Follow(loginUser, findByIdUser);

        followRepository.save(follow);

        return new FollowResponseDto(follow.getFollowerId().getName(), follow.getFollowingId().getName());
    }

    public List<FollowerResponseDto> findFollowerList(HttpSession session) {
        User loginUser = authService.getLoginUser(session);
        List<Follow> findByUserIdFollower = followRepository.findByFollowerId(loginUser);
        if (findByUserIdFollower.isEmpty()) {
            throw new FollowerNotFoundException("팔로워 정보가 없습니다.");
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
            throw new FollowingNotFoundException("팔로잉 정보가 없습니다.");
        }
        List<User> followings = followRepository.findFollowersByFollowingId(loginUser.getId());
        return followings.stream()
                .map(user -> new FollowingResponseDto(user.getName()))
                .collect(Collectors.toList());
    }

    public FollowResponseDto unfollowUser(Long id, HttpSession session) {
        User loginUser = authService.getLoginUser(session);
        Optional<User> optionalFindByIdUser = userRepository.findById(id);
        if (optionalFindByIdUser.isEmpty()) {
            throw new UserNotFoundException("팔로우 취소 할 대상을 찾을 수 없습니다.");
        }
        User findByIdUser = optionalFindByIdUser.get();
        boolean exists = followRepository.existsByFollowerIdAndFollowingId(loginUser, findByIdUser);
        if (!exists) {
            throw new IllegalStateException("팔로우 되어있지 않은 사용자입니다.");
        }
        followRepository.deleteByFollowerIdAndFollowingId(loginUser, findByIdUser);
        return new FollowResponseDto(loginUser.getName(),findByIdUser.getName());
    }
}
