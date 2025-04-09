package com.example.news_feed.follow.service;

import com.example.news_feed.follow.dto.FollowRequestDto;
import com.example.news_feed.follow.dto.FollowerResponseDto;
import com.example.news_feed.follow.dto.FollowingResponseDto;
import com.example.news_feed.follow.entity.Follow;
import com.example.news_feed.follow.repository.FollowRepository;
import com.example.news_feed.user.entity.User;
import com.example.news_feed.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final HttpSession httpSession;


    // Follow 하기(존재할 시 언팔로우)
    @Override
    public void registerFollow(FollowRequestDto followRequestDto, String loginEmail) {
        // 세션에서 내 email 받아오기
        // false로 해야 세션을 새로 생성하지 않음, default가 true <- 로그인할 때만 하는 게 좋음
        // controller부분에서@SessionAttribute(name = "key") String loginEmail 설정하면 서비스에서 받아올 필요 없음
        // HttpSession session = request.getSession(false);

        // 세션 다운 캐스팅... ?
        // String myEmail = (String) session.getAttribute("user");

        // 세션에서 받아온 내 email의 user_id 구하기
        Optional<User> me = userRepository.findByEmail(loginEmail);
        // myEmail로 조회한 DB가 존재할때만 실행
        // bollean 코드 쓰기
        Long myId = null;
        if (me.isPresent()) {
            // user에 me의 객체 정보 저장
            User user = me.get();
            // user에서 user_id값만 추출
            myId = user.getId();
        }

        // follow할 email의 user_id 구하기
        String followEmail = followRequestDto.getEmail();
        Optional<User> followUser = userRepository.findByEmail(followEmail);

        // findyByEmail은 사용하되, orElseThrow <- 사용하기
        Long followId = null;
        if (followUser.isPresent()) {
            // user에 followUser의 객체 정보 저장
            User user = followUser.get();
            // user에서 user_id값만 추출
            followId = user.getId();
        }

        // followRequestDto에서 받아온 email이 내 email에 follow 되어 있는지 확인
        // 이 과정에서 내 email의 id값이 필요함 <- follow 테이블은 user_id에 참조되어있기 때문
        // 추가로 follow한 email또한 id값을 알아야 함 <- follow 테이블에 저장된 값 또한 id로 되어 있을 거라서
        // 짚고 넘어가야 할 점 - DB저장될 때, myId가 follower가 되는거고 followId가 following이 된다.
        // 이후 조회 시, follower <- myId라고 하고 following을 조회하면 내가 follow하는 목록을 조회하는 느낌?

        // followRequestDto에서 받아온 email이 내 email에 follow 되어 있는지 확인
        //
        Boolean isfollowing = followRepository.existsByFollowerIdAndFollowingId(myId, followId);

        // 팔로우한 상태가 아니라면 등록
        if (Boolean.FALSE.equals(isfollowing)) {
            Follow follow = new Follow(myId, followId);
            followRepository.save(follow);
        }
/*
        // 존재한다면 팔로우취소(언팔로우)
        else {
            // Exception 처리

        }


 */
    }
    // Follow 취소
    @Override
    public void deleteFollow(FollowRequestDto followRequestDto, String loginEmail) {
        // 세션에서 받아온 내 email의 user_id 구하기
        Optional<User> me = userRepository.findByEmail(loginEmail);
        // myEmail로 조회한 DB가 존재할때만 실행
        // bollean 코드 쓰기
        Long myId = null;
        if (me.isPresent()) {
            // user에 me의 객체 정보 저장
            User user = me.get();
            // user에서 user_id값만 추출
            myId = user.getId();
        }

        // follow할 email의 user_id 구하기
        String followEmail = followRequestDto.getEmail();
        Optional<User> followUser = userRepository.findByEmail(followEmail);

        // findyByEmail은 사용하되, orElseThrow <- 사용하기
        Long followId = null;
        if (followUser.isPresent()) {
            // user에 followUser의 객체 정보 저장
            User user = followUser.get();
            // user에서 user_id값만 추출
            followId = user.getId();
        }


    }
    // Follower(나를 팔로잉하는 사람들) 목록 조회
    @Override
    public List<FollowerResponseDto> findFollowers(String loginEmail) {
        // 세션에서 받아온 내 email의 user_id 구하기
        Optional<User> me = userRepository.findByEmail(loginEmail);
        // myEmail로 조회한 DB가 존재할때만 실행
        Long myId = null;
        if (me.isPresent()) {
            // user에 me의 객체 정보 저장
            User user = me.get();
            // user에서 user_id값만 추출
            myId = user.getId();
        }
        // myId를 followingId라고 세팅하고, 대응하는 followerId를 List로 가져오기
        List<Follow> followerIdList = followRepository.findEmailByFollowingId(myId);
        // 가져온 followingId List의 followingId들을 user 테이블에서 email로 변환


        return followRepository.findByFollwingId();
    }

    // Following(내가 팔로잉 한 사람들) 목록 조회
    @Override
    public List<FollowingResponseDto> findFollowings(String loginEmail) {
        // 세션에서 받아온 내 email의 user_id 구하기
        Optional<User> me = userRepository.findByEmail(loginEmail);
        // myEmail로 조회한 DB가 존재할때만 실행
        Long myId = null;
        if (me.isPresent()) {
            // user에 me의 객체 정보 저장
            User user = me.get();
            // user에서 user_id값만 추출
            myId = user.getId();
        }
        // myId를 followerId라고 세팅하고, 대응하는 followingId를 List로 가져오기
        List<Follow> followingIdList = followRepository.findEmailByFollowerId(myId);
        // 가져온 followingId List의 followingId들을 user 테이블에서 email로 변환



        return followRepository.findEmailByFollowerId();
    }








    // 나중에 친구 게시물 보기 등의 기능 또한 고려...
    // private final BoardRepository boardRepository;



}
