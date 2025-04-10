package com.example.news_feed.board.repository;

import com.example.news_feed.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {

}
