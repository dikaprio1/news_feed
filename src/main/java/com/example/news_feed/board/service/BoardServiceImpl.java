package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;;


    // 게시물 조회
    //boardRepository.findById(id)는 Optional<Board>를 반환함
    //orElseThrow():게시글이 없을경우 예외발생
    @Override
    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        );
        return new BoardResponseDto(board); //조회한 Board엔티티를 클라이언트에게 보여주기위한 응답DTO로 변환
    }


    // 게시물목록 조회
    @Override
    public List<BoardResponseDto> getAll() {

        List<Board> boardList = boardRepository.findAll(); //모든게시글을 DB에서 다 꺼내옴, 리턴타입은 List<Board> -> 엔티티리스트
        List<BoardResponseDto> responseDtoList = new ArrayList<>(); //클라이언트에게 보낼 응답용 DTO리스트를 만들기위한 빈 리스트
        for(Board board : boardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            responseDtoList.add(boardResponseDto);
        } //엔티티를 하나씩 순회하면서 BoardResponseDto로 변환 -> 변환된 리스트는 응답리스트에 추가됨
        return responseDtoList; //최종적으로 클라이언트에게 게시글 전체 목록을 응답함
    }

    // 게시물 수정
    // 수정작업은 데이터가 변경되므로 트랜잭션이 꼭 필요, 로그인된 사용자 이메일 가져오려면 HttpSession 필요
    @Transactional
    @Override
    public void update(Long id, BoardRequestDto boardRequestDto, HttpSession session) {
        String email = (String) session.getAttribute("user"); // 세션에서 "user"키로 저장된 email 값 꺼냄

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        ); // 수정할 게시물이 DB에 존재하는지 확인 -> 없으면 예외 발생시켜 수정 막음
        if (board.getUser() == null || board.getUser().getEmail() == null ||
                !board.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 삭제 권한이 없습니다");
        } // 작성자 없거나, 이메일 없거나, 로그인 사용자와 이메일 일치하지않으면 403 예외

        board.update(boardRequestDto); //Board 엔티티안에 정의된 update() 메서드 호출
    }

}



