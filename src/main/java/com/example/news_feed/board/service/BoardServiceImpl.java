package com.example.news_feed.board.service;


import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;


    // 게시물 조회
    @Override
    public BoardResponseDto getById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        );
        return new BoardResponseDto(board);
    }
    // 게시물목록 조회
    @Override
    public List<BoardResponseDto> getAll() {
        List<BoardResponseDto> responseDtoList = new ArrayList<>();

        List<Board> boardList = boardRepository.findAll();
        for(Board board : boardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            responseDtoList.add(boardResponseDto);
        }
        return responseDtoList;
    }

    // 게시물 수정
    @Override
    public void update(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        );
        board.update(boardRequestDto);
        boardRepository.save(board);
    }
}



