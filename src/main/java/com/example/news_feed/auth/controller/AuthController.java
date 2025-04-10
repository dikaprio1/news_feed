package com.example.news_feed.auth.controller;

import com.example.news_feed.auth.dto.LoginRequestDto;
import com.example.news_feed.auth.dto.SignupRequestDto;

import com.example.news_feed.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 회원가입, 로그인

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 POST /api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup( @Valid @RequestBody SignupRequestDto requestDto){
        authService.signup(requestDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED); // 가입 성공시 201
    }

    // 로그인 POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<String> login( @Valid @RequestBody LoginRequestDto requestDto, HttpServletRequest request){
        authService.login(requestDto,request);
        return new ResponseEntity<>("로그인 성공", HttpStatus.OK); // 로그인 성공시 200
    }

}