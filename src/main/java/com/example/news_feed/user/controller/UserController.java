package com.example.news_feed.user.controller;

import com.example.news_feed.user.dto.DeleteResponseDto;
import com.example.news_feed.user.dto.UpdateNamePwRequestDto;
import com.example.news_feed.user.dto.DeleteRequestDto;
import com.example.news_feed.user.dto.UserResponseDto;
import com.example.news_feed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 유저 관리

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프로필 조회 GET api/users/{id} 포스트맨x
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        UserResponseDto userResponseDto = userService.findById(id);
        return new ResponseEntity<>(userResponseDto,HttpStatus.OK); // 조회 성공시 200
    }

    // 프로필 수정 PATCH api/users/{id} -> 이름, 비번
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateNameAndPw(
            @PathVariable Long id,
            @RequestBody UpdateNamePwRequestDto requestDto
    ){
        userService.updateNameAndPw(id,requestDto);
        return new ResponseEntity<>("프로필 수정 완료", HttpStatus.OK); // 수정 성공시 200 + 메세지 "프로필 수정 완료"
    }

    // 회원 탈퇴 DELETE? /api/users/{id} -> 완삭 아니고 소프트딜리트
    @DeleteMapping("/{id}")
    private ResponseEntity<DeleteResponseDto> delete(@PathVariable Long id, DeleteRequestDto requestDto){
        DeleteResponseDto deleteDate = userService.delete(id, requestDto);
        return new ResponseEntity<>(deleteDate, HttpStatus.OK); // 삭제 성공시 200
    }

}
