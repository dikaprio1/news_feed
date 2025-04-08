package com.example.news_feed.board.controller;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.service.BoardService;
import com.example.news_feed.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;

    // 게시물목록 조회
    @GetMapping
    public List<BoardResponseDto> getAll() {
        return boardService.getAll();
    }

    // 게시물 조회
    @GetMapping("/{id}")
    public BoardResponseDto getById(
            @PathVariable Long id
    ) {
        return boardService.getById(id);
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto) {
        boardService.update(id, boardRequestDto);
        return ResponseEntity.ok().build();
    }



}
