package com.example.news_feed.exceptionhandler;

public class FollowingNotFoundException extends RuntimeException {
    public FollowingNotFoundException(String message) {
        super(message);
    }
}
