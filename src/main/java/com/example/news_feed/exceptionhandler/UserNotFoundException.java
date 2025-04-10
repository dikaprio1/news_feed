package com.example.news_feed.exceptionhandler;

//유저 값이 NULL일때 필요한 예외 처리
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
    super(message);
    }
}
