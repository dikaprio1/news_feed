package com.example.news_feed.exceptionhandler;

// 사용자 입력 오류 400
public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException(String message) {
        super(message);
    }
}
