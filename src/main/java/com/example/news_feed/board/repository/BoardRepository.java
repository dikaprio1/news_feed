package com.example.news_feed.board.repository;

import com.example.news_feed.board.entity.Board;
import com.example.news_feed.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {
    // Page 사용 전 메서드
    // List<Board> findByUserInOrderByCreateAtDesc(List<User> users);

    Page<Board> findByUserIn(List<User> users, Pageable pageable);

}