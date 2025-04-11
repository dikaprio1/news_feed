package com.example.news_feed.board.service;


import com.example.news_feed.board.dto.DeletePostRequestDto;
import com.example.news_feed.board.dto.DeleteResponseDto;
import com.example.news_feed.board.dto.BoardRequestDto;
import com.example.news_feed.board.dto.BoardResponseDto;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.List;

public interface BoardService {


    // 게시물 조회
    BoardResponseDto findById(Long id);

    List<BoardResponseDto> getAll();
    BoardResponseDto createPosts(BoardRequestDto boardRequestDto, HttpSession session);

    DeleteResponseDto deletePost(Long id, DeletePostRequestDto requestDto, HttpSession session);


    // 뉴스피드 조회(팔로잉하는 유저들의 게시글 최신순<-생성순 으로)
    List<BoardResponseDto> getNewsFeed(HttpSession session);

    @Transactional
    void update(Long id, BoardRequestDto boardRequestDto, HttpSession session);
}

