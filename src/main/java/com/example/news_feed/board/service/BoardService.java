package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface BoardService {
    BoardResponseDto createPosts(BoardRequestDto boardRequestDto);
}
