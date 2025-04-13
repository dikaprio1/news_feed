package com.example.news_feed.follow.repository;

import com.example.news_feed.follow.dto.FollowResponseDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerIdAndFollowingId(User followerId, User followingId);


    List<Follow> findByFollowerId(User followerId);
    List<Follow> findByFollowingId(User followingId);

    @Query("SELECT f.followingId FROM Follow f WHERE f.followerId.id = :followerId")
    List<User> findFollowingsByFollowerId(@Param("followerId") Long followerId);


    @Query("SELECT f.followerId FROM Follow f WHERE f.followingId.id = :followingId")
    List<User> findFollowersByFollowingId(@Param("followingId") Long followingId);


    void deleteByFollowerIdAndFollowingId(User followerId, User followingId);
}
