package com.example.news_feed.exceptionhandler;

public class BoardForbiddenException extends RuntimeException {
    public BoardForbiddenException(String message) {
        super(message);
    }
}
