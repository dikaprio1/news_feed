package com.example.news_feed.board.controller;

import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import com.example.news_feed.board.service.BoardServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
// final이 붙은 필드를 자동으로 생성자 주입
@RequiredArgsConstructor
public class BoardController {

    // 서비스클래스 주입
    private final BoardServiceImpl boardService;

    // 게시물목록 조회
    //ResponseEntity : 응답을 커스터마이징하게해주는 클래스
    //ResponseEntity로 감쌈으로 인해 http응답코드 직접설정가능, 헤더설정, 본문까지 포함한 전체응답 컨트롤 가능
    //게시물 전체목록을 JSON으로 응답할때 각 게시물이 BoardResponseDto형식이고, 그게 리스트로 감싸져있음
    //:: 게시글목록전체를 조회해서, BoardResponseDto형태의 리스트를 HTTP응답으로 반환하고, 상태코드도 직접설정가능하다!
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAll() {
        return ResponseEntity.ok(boardService.getAll());
    }

    // 게시물 조회
    // findById(@PathVariable Long id) : URL경로에서 {id}값을 변수로 추출
    // 반환타입 : ResponseEntity<BoardResponseDto>
    // ㄴ> 게시글 정보를 담은 DTO를 클라이언트에게 응답
    //서비스레이어에 작업을 맡김 : boardService.findById(id) : DB에서 게시글 조회후 DTO로 변환해줌
    //상태코드 200 OK , 응답본문 : responseDto
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> findById(@PathVariable Long id) {
        BoardResponseDto responseDto = boardService.findById(id);
        return ResponseEntity.ok(responseDto);
    }



    // 게시물 수정
    //ResponseEntity<void> : 본문없이 상태코드만 전달하겠다는 뜻
    //update(@PathVariable Long id) : URL경로에서 수정할 게시글의 id 추출
    //@RequestBody BoardRequestDto boardRequestDto : 클라이언트가 보낸 JSON 데이터를 자바객체로 변환
   //boardService.update(id, boardRequestDto): 서비스계층에 수정로직 위임, DB에서 게시글찾고, 제목/내용 수정후 저장처리
   //return ResponseEntity.ok().build() : 200OK 응답본문없음, .build()쓰면 void타입응답을 깔끔히 반환가능
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpSession session) {
        boardService.update(id, boardRequestDto, session);
        return ResponseEntity.ok().build();
    }



}
