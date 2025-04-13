package com.example.news_feed.exceptionhandler;

public class BoardBadRequestException extends RuntimeException {
    public BoardBadRequestException(String message) {
        super(message);
    }
}
