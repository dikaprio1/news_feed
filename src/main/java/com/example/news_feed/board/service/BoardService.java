package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.DeletePostRequestDto;
import com.example.news_feed.board.dto.DeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import jakarta.servlet.http.HttpSession;

public interface BoardService {
    BoardResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session);

    DeleteResponseDto deletePost(Long id, DeletePostRequestDto requestDto, HttpSession session);
}

