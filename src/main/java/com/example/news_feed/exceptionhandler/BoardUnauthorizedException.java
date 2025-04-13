package com.example.news_feed.exceptionhandler;

public class BoardUnauthorizedException extends RuntimeException {
    public BoardUnauthorizedException(String message) {
        super(message);
    }
}
