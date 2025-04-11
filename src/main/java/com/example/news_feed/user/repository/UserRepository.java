package com.example.news_feed.user.repository;

import com.example.news_feed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // id로 조회
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 아이디입니다.")); // 조회 실패시(해당 id 없을 때) 404, "존재하지 않는 정보입니다."
    }

    // id로 조회
    Optional<User> findById(Long id);

    //이메일 조회
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 이메일입니다.")); // 조회 실패시(해당 id 없을 때) 404, "존재하지 않는 정보입니다."
    }

    // 이메일로 조회
    Optional<User> findByEmail(String email);
}

