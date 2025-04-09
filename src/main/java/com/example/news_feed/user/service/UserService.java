package com.example.news_feed.user.service;

import com.example.news_feed.user.dto.*;

public interface UserService {

    UserResponseDto findById(Long id);

    DeleteResponseDto delete(Long id, DeleteRequestDto requestDto);

    void updateName(Long id, updateNameRequestDto requestDto);

    void updatePassword(Long id, updatePwRequestDto requestDto);

}

