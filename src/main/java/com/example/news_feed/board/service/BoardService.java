package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardDeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;

public interface BoardService {
    BoardResponseDto createPosts(BoardRequestDto boardRequestDto);


    BoardDeleteResponseDto deletePost(Long id);
}
