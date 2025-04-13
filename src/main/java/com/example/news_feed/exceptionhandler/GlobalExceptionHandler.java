package com.example.news_feed.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // 사용자를 찾을 수 없을 때 (예: ID가 없는 경우)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    // 팔로우를 찾을 수 없을 때 (예 : 사용자의 팔로우가 한 명도 없는 경우)
    @ExceptionHandler(FollowNotFoundException.class)
    public ResponseEntity<String> handleFollowerNotFoundException(FollowNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 자기 자신을 팔로우 하려 할 때
    @ExceptionHandler(FollowMySelfException.class)
    public ResponseEntity<String> handleFollowerNotFoundException(FollowMySelfException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    // 팔로우를 찾을 수 없을 때
    @ExceptionHandler(AlreadyFollowerException.class)
    public ResponseEntity<String> handleFollowerNotFoundException(AlreadyFollowerException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 팔로우 되어있지 않은 사용자를 삭제하려 할 때
    @ExceptionHandler(NotFollowDeleteException.class)
    public ResponseEntity<String> handleFollowerNotFoundException(NotFollowDeleteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    // 모든 예외 처리 (예상하지 못한 오류)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + ex.getMessage());
    }
}
