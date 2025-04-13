package com.example.news_feed.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    // @Valid 관련 오류 처리 -> 메세지만 띄움
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put("message", error.getDefaultMessage()); // 필드명 : 메시지
        }

        return ResponseEntity.badRequest().body(errors); // 400
    }

    // 중복 회원가입 409
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUser(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // 로그인 실패 401
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<String> handleInvalidLogin(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 사용자 입력 오류 400
    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<String> handleInvalidUserInput(InvalidUserInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // 본인 계정에만 접근 가능 403
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccess(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }



}
