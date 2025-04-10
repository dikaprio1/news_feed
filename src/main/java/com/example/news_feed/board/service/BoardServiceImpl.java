package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;


    // 게시물 조회
    //boardRepository.findById(id)는 Optional<Board>를 반환함
    //orElseThrow():게시글이 없을경우 예외발생
    //return new BoardResponseDto(board) : 조회한 Board엔티티를 클라이언트에게 보여주기위한 응답DTO로 변환
    @Override
    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        );
        return new BoardResponseDto(board);
    }


    // 게시물목록 조회
    //List<Board> boardList = boardRepository.findAll() : 모든게시글을 DB에서 다 꺼내옴, 리턴타입은 List<Board> -> 엔티티리스트
    //List<BoardResponseDto> responseDtoList = new ArrayList<>(); : 클라이언트에게 보낼 응답용 DTO리스트를 만들기위한 빈 리스트
    //엔티티를 하나씩 순회하면서 BoardResponseDto로 변환 -> 변환된 리스트는 응답리스트에 추가됨
    //return responseDtoList : 최종적으로 클라이언트에게 게시글 전체 목록을 응답함
    @Override
    public List<BoardResponseDto> getAll() {

        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDto> responseDtoList = new ArrayList<>();
        for(Board board : boardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            responseDtoList.add(boardResponseDto);
        }
        return responseDtoList;
    }

    // 게시물 수정
    // 수정작업은 데이터가 변경되므로 트랜잭션이 꼭 필요함
    // 58,59: 수정할 게시물이 DB에 존재하는지 확인 -> 없으면 예외 발생시켜 수정 막음
    //board.update(boardRequestDto)
    //ㄴ>Board엔티티안에 정의된 update() 메서드 호출
    @Transactional
    @Override
    public void update(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        );
        board.update(boardRequestDto);
    }

}



