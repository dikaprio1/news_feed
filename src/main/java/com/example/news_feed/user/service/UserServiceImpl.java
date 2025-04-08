package com.example.news_feed.user.service;

import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.user.dto.UpdateNamePwRequestDto;
import com.example.news_feed.user.dto.UserResponseDto;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    @Override
    public void updateNameAndPw(Long id, UpdateNamePwRequestDto requestDto) { // 이후 로그인한 사용자만 사용할 수 있게 변경 필요

        // 이름만 바꾸는경우에도 pw입력해야함(not null) pw만 바꾸는 경우에는 이름 null이어도 됨
        // 이름 null이면 걍 이름은 원래 db에 있는걸로 두면 되고 pw만 저장
        // 둘 다 바꾸면 둘 다 저장해주면 됨

        // 어려워서 일단 비번 수정만 구현함
        User findUser = userRepository.findByIdOrElseThrow(id);

        if (!passwordEncoder.matches(requestDto.getOldPassword(), findUser.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"); // 401 반환
        }

        if (passwordEncoder.matches(requestDto.getNewPassword(), findUser.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "현재 비밀번호와 동일합니다"); // 401 반환
        }

        findUser.updatePassword(passwordEncoder.encode(requestDto.getNewPassword())); // 새 비번 저장



    }

}
