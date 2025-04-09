package com.example.news_feed.user.service;

import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.user.dto.*;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto findById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        return new UserResponseDto(findUser.getName(), findUser.getEmail(), findUser.getGender(), findUser.getAge());
    }

    @Transactional
    @Override
    public void updateName(Long id, updateNameRequestDto requestDto) {

        User findUser = userRepository.findByIdOrElseThrow(id); //id 체크

        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())) { // 비번체크
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"); // 401 반환
        }

        if (requestDto.getUsername().equals(findUser.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 이름과 동일합니다");  // 400 반환
        }
        findUser.updateName(requestDto.getUsername());

    }

    @Transactional
    @Override
    public void updatePassword(Long id, updatePwRequestDto requestDto) {

        User findUser = userRepository.findByIdOrElseThrow(id); //id 체크

        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())) { // 비번체크
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"); // 401 반환
        }

        if (passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "현재 비밀번호와 동일합니다"); // 401 반환
            }
            findUser.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }

    @Transactional
    @Override
    public DeleteResponseDto delete(Long id, DeleteRequestDto requestDto) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!findUser.getEmail().equals(requestDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다"); // 아이디 불일치 401 반환
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"); // 비번 불일치 401 반환
        }

        if (findUser.getDeletedAt() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT); // 이미 탈퇴한 회원 409 반환
        }

        LocalDateTime now = LocalDateTime.now();
        findUser.saveDeleteTime(now);
        return new DeleteResponseDto(findUser.getDeletedAt(), "회원탈퇴 성공");
    }


}
