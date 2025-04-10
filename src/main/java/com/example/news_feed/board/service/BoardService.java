package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import java.util.List;

public interface BoardService {


    // 게시물 조회
    BoardResponseDto findById(Long id);

    List<BoardResponseDto> getAll();

    // 게시물 수정
    void update(Long id, BoardRequestDto boardRequestDto);
}
