package com.example.news_feed.exceptionhandler;

public class FollowerNotFoundException extends RuntimeException {
    public FollowerNotFoundException(String message) {
        super(message);
    }
}
