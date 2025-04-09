package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardDeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    @Override
    public BoardResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session) {
        Board board = new Board(boardRequestDto.getTitle(),
                boardRequestDto.getContents(),
                boardRequestDto.getImage());
        String email = (String) session.getAttribute("user");

        User finduser = userRepository.findByEmailOrElseThrow(email);

        board.setUser(finduser);

        Board savedBoard = boardRepository.save(board); // 엔티티 저장


        return new BoardResponseDto(board.getTitle(),finduser.getName(),board.getContent(),board.getImage());
    }

    @Override
    public BoardDeleteResponseDto deletePost (Long id){
       Board board = boardRepository.findById(id) // 삭제할 게시물 조회
                .orElseThrow(() -> new RuntimeException("게시글을 찾을수 없습니다. " + id)); // 값이 없을경우 예외처리

        boardRepository.delete(board); // 게시물 하드삭제

        LocalDateTime deletedAt = LocalDateTime.now(); // 삭제 시간 기록

        return new BoardDeleteResponseDto(board.getId(), deletedAt,"삭제되었습니다.");
    }

}
