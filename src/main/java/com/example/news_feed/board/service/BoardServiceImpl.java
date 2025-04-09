package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    @Override
    public BoardResponseDto createPosts(BoardRequestDto boardRequestDto) {
        return new BoardResponseDto(boardRequestDto.getTitle(),
                boardRequestDto.getAuthor(),
                boardRequestDto.getContent(),
                boardRequestDto.getImage(),
                LocalDateTime.now());
    }

}
