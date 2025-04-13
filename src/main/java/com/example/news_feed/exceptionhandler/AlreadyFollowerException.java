package com.example.news_feed.exceptionhandler;

public class AlreadyFollowerException extends RuntimeException {
    public AlreadyFollowerException(String message) {
        super(message);
    }
}
