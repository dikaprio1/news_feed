package com.example.news_feed.board.repository;

import com.example.news_feed.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
