package com.example.news_feed.auth.service;

import com.example.news_feed.auth.dto.LoginRequestDto;
import com.example.news_feed.auth.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    void signup(SignupRequestDto requestDto);

    void login(LoginRequestDto requestDto, HttpServletRequest request);

}