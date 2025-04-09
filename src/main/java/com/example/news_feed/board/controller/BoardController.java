package com.example.news_feed.board.controller;


import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping // 게시물 생성
    public ResponseEntity<BoardResponseDto> createPosts(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createPosts(boardRequestDto);
        return ResponseEntity.ok(boardResponseDto);
    }

    @DeleteMapping("/{id}") // 게시물 삭제
    public
}
