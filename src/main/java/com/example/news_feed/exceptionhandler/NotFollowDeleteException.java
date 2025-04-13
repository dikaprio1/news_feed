package com.example.news_feed.exceptionhandler;

public class NotFollowDeleteException extends RuntimeException {
    public NotFollowDeleteException(String message) {
        super(message);
    }
}
