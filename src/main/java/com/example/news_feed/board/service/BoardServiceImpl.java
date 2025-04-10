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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public BoardResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session) {
        Board board = new Board(boardRequestDto.getTitle(),
                boardRequestDto.getContent(),
                boardRequestDto.getImage());
        String email = (String) session.getAttribute("user");

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"로그인이 필요합니다"); // 로그인을안했을경우 , 401 반환
        }

        if (boardRequestDto.getTitle() == null || boardRequestDto.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수입니다"); // 제목이 null 일경우, 400 반환
        }

        if (boardRequestDto.getContent() == null || boardRequestDto.getContent().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수입니다"); // 내용이 null 일경우 ,400 반환
        }


        User finduser = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"이메일을 찾을수없습니다")); // DB에 이메일이 없을경우 , 404 반환


        board.setUser(finduser);

        if (finduser.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"작성자 이름이 존재하지 않습니다"); // author(작성자)가 null 일경우 400 반환
        }

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

        if (!email.equals(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "로그인된 유저와 이메일이 일치하지 않습니다"); // 세션이메일과 바디의이메일이 다를경우 , 403 반환
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다")); // 유저를 찾지못한경우 , 404 반환

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
