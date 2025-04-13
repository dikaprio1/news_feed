package com.example.news_feed.board.service;


import com.example.news_feed.board.dto.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.List;

public interface BoardService {


    // 게시물 조회
    BoardResponseDto findById(Long id);

    List<BoardResponseDto> getAll();
    BoardCreatedResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session);

    DeleteResponseDto deletePost(Long id, DeletePostRequestDto requestDto, HttpSession session);


    @Transactional
    void update(Long id, BoardRequestDto boardRequestDto, HttpSession session);
}

