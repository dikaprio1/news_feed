package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardResponseDto;

import java.util.List;

public interface BoardService {


    BoardResponseDto getById(Long id);

    List<BoardResponseDto> getAll();
}
