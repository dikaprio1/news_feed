package com.example.news_feed.board.service;

import com.example.news_feed.auth.service.AuthServiceImpl;
import com.example.news_feed.board.dto.DeletePostRequestDto;
import com.example.news_feed.board.dto.DeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.entity.Board;
import com.example.news_feed.board.repository.BoardRepository;
import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.exceptionhandler.FollowNotFoundException;
import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.entity.User;
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
    private final FollowRepository followRepository;
    private final AuthServiceImpl authService;



    // 게시물 조회
    //boardRepository.findById(id)는 Optional<Board>를 반환함
    //orElseThrow():게시글이 없을경우 예외발생
    @Override
    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시물이 존재하지 않습니다")
        );
        return new BoardResponseDto(board.getId(),board.getTitle(),board.getContent(),board.getImageUrl(),board.getUser().getName()); //조회한 Board엔티티를 클라이언트에게 보여주기위한 응답DTO로 변환
    }


    // 게시물목록 조회
    @Override
    public List<BoardResponseDto> getAll() {
        List<Board> boardList = boardRepository.findAll(); //모든게시글을 DB에서 다 꺼내옴, 리턴타입은 List<Board> -> 엔티티리스트
        List<BoardResponseDto> responseDtoList = new ArrayList<>(); //클라이언트에게 보낼 응답용 DTO리스트를 만들기위한 빈 리스트
        for(Board board : boardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board.getId(),board.getTitle(),board.getContent(),board.getImageUrl(),board.getUser().getName());
            responseDtoList.add(boardResponseDto);
        } //엔티티를 하나씩 순회하면서 BoardResponseDto로 변환 -> 변환된 리스트는 응답리스트에 추가됨
        return responseDtoList; //최종적으로 클라이언트에게 게시글 전체 목록을 응답함
    }

    // 뉴스피드 조회(팔로잉하는 유저들의 게시글 최신순<-생성순 으로)
    @Override
    public List<BoardResponseDto> getNewsFeed(HttpSession session) {

        // 세션으로 로그인 한 유저 객체 만들기
        User loginUser = authService.getLoginUser(session);
        // 로그인 유저의 id값을 이용해서 follow한 following한 사람들의 id값 가져와서 유저 객체 만들기
        List<User> followings = followRepository.findFollowingsByFollowerId(loginUser.getId());
        if (followings.isEmpty()) { // 팔로잉한 사람이 없으니 게시글이 안 나온다는 메세지 출력.
            throw new FollowNotFoundException("팔로잉 정보가 없어 게시글을 로드할 수 없습니다.");
        }
        // 해당 user_id 리스트와 대응되는 게시글을 찾아야 함
        // in을 통해 List조회가 가능해짐,OrderByCreatedAtDesc를 통해 게시글을 최신 생성순으로 정렬.
        List<Board> newsfeedBoardList = boardRepository.findByUserInOrderByCreateAtDesc(followings);
        List<BoardResponseDto>  responseDtoList = new ArrayList<>();
        for(Board board : newsfeedBoardList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board.getId(),board.getTitle(),board.getContent(),board.getImageUrl(),board.getUser().getName());
            responseDtoList.add(boardResponseDto);
        }
        return responseDtoList;

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


        User finduser = userRepository.findByEmailOrElseThrow(email); // 로그인 유저정보 조회
        Board saveboard = new Board(boardRequestDto.getTitle(),boardRequestDto.getContent(),boardRequestDto.getImageUrl(),finduser);
        boardRepository.save(saveboard); // 엔티티 저장


        return new BoardResponseDto(
                saveboard.getId(),
                saveboard.getTitle(),
                saveboard.getContent(),
                saveboard.getImageUrl(),
                finduser.getName());
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



