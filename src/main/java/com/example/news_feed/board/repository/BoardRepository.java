package com.example.news_feed.board.repository;

import com.example.news_feed.board.entity.Board;
import com.example.news_feed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByUserInOrderByCreateAtDesc(List<User> users);

}