package com.example.news_feed.auth.service;

import com.example.news_feed.auth.dto.LoginRequestDto;
import com.example.news_feed.auth.dto.SignupRequestDto;
import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 메서드
    @Override
    public void signup(SignupRequestDto requestDto) {

        // 이메일 중복체크
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            log.warn("이미 존재하는 사용자입니다 : " + requestDto.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다"); // 중복시 409
        }

        // 비번 암호화해서 저장
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword()); // 암호화
        User user = new User( // 암호화된 비밀번호 포함해서 요청으로 받아온 정보들 user에 담기
                requestDto.getUsername(),
                requestDto.getEmail(),
                encodedPassword,
                requestDto.getGender(),
                requestDto.getAge()
        );

        userRepository.save(user); // 저장
    }

    @Override
    public void login(LoginRequestDto requestDto) {
//        Optional<User> optionalUser = userRepository.findIdByEmailAndPassword();
//        if (optionalUser.isPresent()){
//            User user = optionalUser.get();
//            //userRepository.findById()
//        }

    }



}
