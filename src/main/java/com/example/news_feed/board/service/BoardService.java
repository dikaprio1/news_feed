package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardDeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import jakarta.servlet.http.HttpSession;

public interface BoardService {
    BoardResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session);


    BoardDeleteResponseDto deletePost(Long id);
}
