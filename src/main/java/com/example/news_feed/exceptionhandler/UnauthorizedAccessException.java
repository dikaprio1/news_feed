package com.example.news_feed.exceptionhandler;

// 본인 계정만 수정, 삭제 가능 403
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
