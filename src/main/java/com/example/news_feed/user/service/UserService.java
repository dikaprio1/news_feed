package com.example.news_feed.user.service;

import com.example.news_feed.user.dto.*;
import jakarta.servlet.http.HttpSession;

public interface UserService {

    UserResponseDto findById(Long id);

    DeleteResponseDto delete(Long id, DeleteRequestDto requestDto, String sessionEmail);

    void updateName(Long id, updateNameRequestDto requestDto, String sessionEmail);

    void updatePassword(Long id, updatePwRequestDto requestDto, String sessionEmail);

}

