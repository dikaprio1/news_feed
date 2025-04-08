package com.example.news_feed.user.service;

import com.example.news_feed.user.dto.UserResponseDto;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto findById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        return new UserResponseDto(findUser.getName(), findUser.getEmail(), findUser.getGender(), findUser.getAge());
    }
}
