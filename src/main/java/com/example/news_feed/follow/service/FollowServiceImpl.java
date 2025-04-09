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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    // Follow 하기
    @Override
    public void registerFollow(FollowRequestDto followRequestDto, String loginEmail) {

        // controller부분에서@SessionAttribute(name = "key") String loginEmail 설정하면 서비스에서 받아올 필요 없음

        // 세션에서 받아온 내 email의 user_id 구하기
        User me = userRepository.findByEmail(loginEmail).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));

        // follow할 email의 user_id 구하기
        String followEmail = followRequestDto.getEmail();
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));


        // followRequestDto에서 받아온 email이 내 email에 follow 되어 있는지 확인
        // 이 과정에서 내 email의 id값이 필요함 <- follow 테이블은 user_id에 참조되어있기 때문
        // 추가로 follow한 email또한 id값을 알아야 함 <- follow 테이블에 저장된 값 또한 id로 되어 있을 거라서
        // 짚고 넘어가야 할 점 - DB저장될 때, myId가 follower가 되는거고 followId가 following이 된다.
        // 이후 조회 시, follower <- myId라고 하고 following을 조회하면 내가 follow하는 목록을 조회하는 느낌?

        // followRequestDto에서 받아온 email이 내 email에 follow 되어 있는지 확인

        Boolean isFollowing = followRepository.existsByFollowerIdAndFollowingId(me, followUser);

        // 팔로우한 상태가 아니라면 등록
        if (Boolean.FALSE.equals(isFollowing)) {
            Follow follow = new Follow(me.getId(), followUser.getId());
            followRepository.save(follow);
        }
    }


    // Follow 취소
    @Override
    public void deleteFollow(FollowRequestDto followRequestDto, String loginEmail) {
        // 세션에서 받아온 내 email의 user_id 구하기
        User me = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));


        // follow할 email의 user_id 구하기
        String followEmail = followRequestDto.getEmail();
        User followUser = userRepository.findByEmail(followEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));

        Boolean isFollowing = followRepository.existsByFollowerIdAndFollowingId(me, followUser);

        if (Boolean.TRUE.equals(isFollowing)) {
            // 삭제하기 위해 해당하는 팔로우 객체 가져오기.
            Follow follow = followRepository.findByFollowerIdAndFollowingId(me,followUser).orElseThrow(()
                    -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));

            followRepository.delete(follow);
        }
    }




    // Follower(나를 팔로잉하는 사람들) 목록 조회
    @Override
    public List<FollowerResponseDto> findFollowers(String loginEmail) {
        // 세션에서 받아온 내 email의 user_id 구하기
        User me = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));


        // myId를 followingId라고 세팅하고, 대응하는 followerId를 List로 가져오기
        List<Follow> followerIdList = followRepository.findByFollowingId(me);


        // 가져온 followerIdList의 followerId들을
        // user 테이블에서 참조받은 email로 변환하는 과정

        // 이메일을 담을 DTO 리스트 생성
        List<FollowerResponseDto> followerEmailList = new ArrayList<>();

        for (Follow follow : followerIdList) {
            User follower = follow.getFollowerId();
            // userId로 유저 조회 후 이메일 가져오기
            User findUser = userRepository.findById(follower.getId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));
            String followerEmail = findUser.getEmail();

            followerEmailList.add(new FollowerResponseDto(followerEmail));
        }


        return followerEmailList;
    }

    // Following(내가 팔로잉 한 사람들) 목록 조회
    @Override
    public List<FollowingResponseDto> findFollowings(String loginEmail) {
        // 세션에서 받아온 내 email의 user_id 구하기
        User me = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));

        // myId를 followerId라고 세팅하고, 대응하는 followingId를 담을 List 생성
        List<Follow> followingIdList = followRepository.findByFollowingId(me);


        List<FollowingResponseDto> followingEmailList = followingIdlist.


        /*
        List<Follow> followingIdList = new ArrayList<>();
        // for문을 통해 넣기
        for ()
         */


        // 가져온 followingIdList의 followingId들을
        // user 테이블에서 참조받은 email로 변환하는 과정

        // 이메일을 담을 DTO 리스트 생성
        List<FollowingResponseDto> followingEmailList = new ArrayList<>();

        for (Follow follow : followingIdList) {
            User following = follow.getFollowingId();
            // userId로 유저 조회 후 이메일 가져오기
            User findUser = userRepository.findById(following.getId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));
            String followingEmail = findUser.getEmail();

            followingEmailList.add(new FollowingResponseDto(followingEmail));
        }

        return followingEmailList;
    }








    // 나중에 친구 게시물 보기 등의 기능 또한 고려...
    // private final BoardRepository boardRepository;



}
