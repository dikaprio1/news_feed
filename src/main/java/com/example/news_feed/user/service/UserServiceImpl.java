package com.example.news_feed.user.service;

import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.exceptionhandler.InvalidLoginException;
import com.example.news_feed.exceptionhandler.InvalidUserInputException;
import com.example.news_feed.exceptionhandler.UnauthorizedAccessException;
import com.example.news_feed.exceptionhandler.UserNotFoundException;
import com.example.news_feed.user.dto.*;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
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

    //프로필 조회
    @Override
    public UserResponseDto findById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        if (findUser.getDeletedAt() != null){
            throw new UserNotFoundException("사용자를 찾을 수 없습니다"); // 이미 탈퇴한 회원 404
        }

        return new UserResponseDto(findUser.getName(), findUser.getEmail(), findUser.getGender(), findUser.getAge());
    }

    //이름수정
    @Transactional
    @Override
    public void updateName(Long id, updateNameRequestDto requestDto, Long loginId) {

        User findUser = userRepository.findByIdOrElseThrow(id); //id 체크

        if (!findUser.getId().equals(loginId)) {
            throw new UnauthorizedAccessException("본인 계정만 수정할 수 있습니다."); // 로그인한 회원과 대상 유저 동일한지 확인
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())) { // 비번체크
            throw new InvalidLoginException("비밀번호가 일치하지 않습니다"); // 401 반환
        }

        if (requestDto.getUsername().isBlank()) {
            throw new InvalidUserInputException("이름을 입력해주세요"); // 변경할 이름 공란일 시 400 반환
        }

        if (requestDto.getUsername().equals(findUser.getName())) {
            throw new InvalidUserInputException("기존 이름과 동일합니다");  // 400 반환
        }
        findUser.updateName(requestDto.getUsername());

    }

    //비번수정 /
    @Transactional
    @Override
    public void updatePassword(Long id, updatePwRequestDto requestDto, Long loginId) {

        User findUser = userRepository.findByIdOrElseThrow(id); //id 체크

        if (!findUser.getId().equals(loginId)) {
            throw new UnauthorizedAccessException("본인 계정만 수정할 수 있습니다."); // 로그인한 회원과 대상 유저 동일한지 확인
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())) { // 비번체크
            throw new InvalidLoginException("비밀번호가 일치하지 않습니다"); // 401 반환
        }

        if (passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())) {
            throw new InvalidUserInputException("현재 비밀번호와 동일합니다"); // 400 반환
            }
            findUser.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
    }

    //회원탈퇴
    @Transactional
    @Override
    public DeleteResponseDto delete(Long id, DeleteRequestDto requestDto, Long loginId) {

        User findUser = userRepository.findByIdOrElseThrow(id); // 탈퇴 대상 유저 조회

        if (!findUser.getId().equals(loginId)) {
            throw new UnauthorizedAccessException("본인 계정만 삭제할 수 있습니다."); // 로그인한 회원과 대상 유저 동일한지 확인
        }

        if (!findUser.getEmail().equals(requestDto.getEmail())) {
            throw new InvalidLoginException("아이디 또는 비밀번호가 올바르지 않습니다"); // 아이디 불일치 401 반환
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new InvalidLoginException("아이디 또는 비밀번호가 올바르지 않습니다"); // 비번 불일치 401 반환
        }

        if (findUser.getDeletedAt() != null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다"); // 이미 탈퇴한 회원 404
        }

        LocalDateTime now = LocalDateTime.now();
        findUser.setDeleteTime(now);
        return new DeleteResponseDto(findUser.getDeletedAt(), "회원탈퇴 성공");
    }


}

