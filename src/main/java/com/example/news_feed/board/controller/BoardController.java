package com.example.news_feed.board.controller;


import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.dto.DeletePostRequestDto;
import com.example.news_feed.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping // 게시물 생성
    public ResponseEntity<BoardResponseDto> createPosts(@Valid @RequestBody BoardRequestDto boardRequestDto, HttpSession session) {
        BoardResponseDto boardResponseDto = boardService.createPosts(boardRequestDto,session);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED); // 201 반환
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosts(@PathVariable Long id,@Valid @RequestBody DeletePostRequestDto requestDto, HttpSession session) {
        boardService.deletePost(id, requestDto, session);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }
}
