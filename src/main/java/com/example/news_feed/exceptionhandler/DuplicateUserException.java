package com.example.news_feed.exceptionhandler;

// 중복 회원가입 409 Conflict
public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
