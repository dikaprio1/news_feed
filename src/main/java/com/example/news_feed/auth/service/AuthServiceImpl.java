package com.example.news_feed.auth.service;

import com.example.news_feed.auth.dto.LoginRequestDto;
import com.example.news_feed.auth.dto.SignupRequestDto;
import com.example.news_feed.config.PasswordEncoder;
import com.example.news_feed.exceptionhandler.UserNotFoundException;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public void login(LoginRequestDto requestDto, HttpServletRequest request) {
        Optional<User> findUser = userRepository.findByEmail(requestDto.getEmail()); // 나중에 리팩토링 고민
        if(findUser.isPresent()){
            User user = findUser.get();
            if(passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
                HttpSession session = request.getSession();
                session.setAttribute("user",user.getEmail());
            }else{
                throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
            }
        }
    }

    public User getLoginUser(HttpSession session) {
        String loginEmail = (String) session.getAttribute("user");
        return userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UserNotFoundException("로그인이 필요합니다."));
    }
}

