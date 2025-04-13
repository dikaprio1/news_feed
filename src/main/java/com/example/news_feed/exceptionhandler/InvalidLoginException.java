package com.example.news_feed.exceptionhandler;

// 입력받은 아이디, 비밀번호가 저장된 데이터와 불일치 시 401 Unauthorized
public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
