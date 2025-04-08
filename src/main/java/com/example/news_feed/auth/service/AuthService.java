package com.example.news_feed.auth.service;

import com.example.news_feed.auth.dto.LoginRequestDto;
import com.example.news_feed.auth.dto.SignupRequestDto;

public interface AuthService {
    void signup(SignupRequestDto requestDto);

    void login(LoginRequestDto requestDto);

}
