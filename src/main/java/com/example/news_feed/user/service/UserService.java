package com.example.news_feed.user.service;


import com.example.news_feed.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto findById(Long id);

}
