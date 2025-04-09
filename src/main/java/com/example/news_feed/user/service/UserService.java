package com.example.news_feed.user.service;

import com.example.news_feed.user.dto.DeleteResponseDto;
import com.example.news_feed.user.dto.UpdateNamePwRequestDto;
import com.example.news_feed.user.dto.DeleteRequestDto;
import com.example.news_feed.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto findById(Long id);

    void updateNameAndPw(Long id, UpdateNamePwRequestDto requestDto);

    DeleteResponseDto delete(Long id, DeleteRequestDto requestDto);

}

