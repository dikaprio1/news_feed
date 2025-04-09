package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardDeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    @Override
    public BoardResponseDto createPosts(BoardRequestDto boardRequestDto) {
        Board board = new Board(); // 엔티티 매핑

        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        board.setImage(boardRequestDto.getImage());
        board.setAuthor(boardRequestDto.getAuthor());
        board.setCreatedAt(LocalDateTime.now());

        Board savedBoard = boardRepository.save(board); // 엔티티 저장

        BoardResponseDto responseDto = new BoardResponseDto(); // 응답
        responseDto.setId(savedBoard.getId());
        responseDto.setTitle(savedBoard.getTitle());
        responseDto.setContent(savedBoard.getContent());
        responseDto.setImage(savedBoard.getImage());
        responseDto.setAuthor(savedBoard.getAuthor());
        responseDto.setCreatedAt(savedBoard.getCreatedAt());

        return responseDto;
    }

    @Override
    public BoardDeleteResponseDto deletePost (Long id){
       Board board = boardRepository.findById(id) // 삭제할 게시물 조회
                .orElseThrow(() -> new RuntimeException("게시글을 찾을수 없습니다. " + id)); // 값이 없을경우 예외처리

        boardRepository.delete(board); // 게시물 하드삭제

        LocalDateTime deletedAt = LocalDateTime.now(); // 삭제 시간 기록

        BoardDeleteResponseDto deleteResponseDto = new BoardDeleteResponseDto(); // 삭제 응답
        deleteResponseDto.setId(id);
        deleteResponseDto.setDeletedAt(deletedAt);
        deleteResponseDto.setMessage("삭제되었습니다");

        return deleteResponseDto;
    }

}
