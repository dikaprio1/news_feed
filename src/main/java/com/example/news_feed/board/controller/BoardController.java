package com.example.news_feed.board.controller;


import com.example.news_feed.board.dto.BoardDeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<BoardResponseDto> createPosts(@RequestBody BoardRequestDto boardRequestDto, HttpSession session) {
        BoardResponseDto boardResponseDto = boardService.createPosts(boardRequestDto,session);
        return new ResponseEntity<>(boardResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosts(@PathVariable Long id){
        boardService.deletePost(id);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }
}
