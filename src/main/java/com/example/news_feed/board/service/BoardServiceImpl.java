package com.example.news_feed.board.service;

import com.example.news_feed.board.dto.DeletePostRequestDto;
import com.example.news_feed.board.dto.DeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
  
  
  
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
  


    @Override
    public BoardResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session) {
        Board board = new Board(boardRequestDto.getTitle(), boardRequestDto.getContent(), boardRequestDto.getImage());
        String email = (String) session.getAttribute("user");

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다"); // 로그인을안했을경우 , 401 반환
        }

        if (boardRequestDto.getTitle() == null || boardRequestDto.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수입니다"); // 제목이 null or 공백일경우, 400 반환
        }

        if (boardRequestDto.getContent() == null || boardRequestDto.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수입니다"); // 내용이 null or 공백일경우 ,400 반환
        }


        User finduser = userRepository.findByEmailOrElseThrow(email); // 로그인시점 조회
        board.setUser(finduser);
        boardRepository.save(board); // 엔티티 저장


        return new BoardResponseDto(board.getTitle(),
                finduser.getName(),
                board.getContent(),
                board.getImage());
    }

    @Override
    public DeleteResponseDto deletePost (Long id, DeletePostRequestDto requestDto, HttpSession session){
        String email = (String) session.getAttribute("user");
        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"); // 로그인을안했을경우 , 401 반환
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다")); // 유저를 찾지못한경우 , 404 반환

        if (!email.equals(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이메일이 일치하지 않습니다"); // 이메일이 다른경우 , 403 반환
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"); // password가 다른경우 , 401 반환
        }

       Board board = boardRepository.findById(id) // 삭제할 게시물 조회
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"게시글이 존재하지 않습니다. " + id)); // 게시물이 없을경우, 404 반환

        if (board.getUser() == null || board.getUser().getEmail() == null ||
                !board.getUser().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "게시글 삭제 권한이 없습니다"); // 작성자 본인만 삭제할수있게 , 403 반환
        }


        boardRepository.delete(board); // 게시물 하드삭제

        LocalDateTime deletedAt = LocalDateTime.now(); // 삭제 시간 기록

        return new DeleteResponseDto(board.getId(),
                deletedAt,
                "삭제되었습니다.");
    }
}



