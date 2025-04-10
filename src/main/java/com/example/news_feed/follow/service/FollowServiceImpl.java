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

import java.time.LocalDateTime;
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

        // 맨 처음에 requestdto의 email과 세션에 있는 email을 비교하는 유효성 검증.
        if (followRequestDto.getEmail().equals(loginEmail))
             {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수는 없습니다.");}

        else {
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
                Follow follow = new Follow(me, followUser);
                followRepository.save(follow);
            }
        }
    }

    // Follow 취소
    @Override
    public void deleteFollow(FollowRequestDto followRequestDto, String loginEmail) {
        // 맨 처음에 requestdto의 email과 세션에 있는 email을 비교하는 유효성 검증.
        if (followRequestDto.getEmail().equals(loginEmail))
        {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우 삭제할 수는 없습니다.");}

        // 세션에서 받아온 내 email의 user_id 구하기
        User me = userRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));


        // follow할 email의 user_id 구하기
        String followEmail = followRequestDto.getEmail();
        User followUser = userRepository.findByEmail(followEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 정보를 찾을 수 없습니다."));

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
        // sql 쿼리 결과를 조회해서 받아오는 식이기 때문에 List로 받아올 수 있음.
        List<Follow> followerIdList = followRepository.findByFollowingId(me);

        // 이메일을 담을 DTO 리스트 생성
        // id에 맞는 email을 각각 뽑아와서 리스트로 넣어야 하니, sql 쿼리 조회 결과를 하나씩 리스트에 넣는 느낌
        // 따라서 결과를 List로 받는 게 아닌, 만들어 놓고 결과를 하나씩 집어넣는다.
        List<FollowerResponseDto> followerEmailList = new ArrayList<>();

        // stream으로도 변환 고려
        // 해당 for문에서 지연로딩의 단점 (n+1) 발생함. (stream, for 동일)
        // 즉시로딩을 사용할 지,
        // (n+1) 해결 방안 사용할 지 [쿼리 문 추가로 넣는 방안 생각 중]
        // JPQL 쿼리문으로 대체하는 방식이 맞는 것 같은데 너무 복잡하고 이해 안 감.
        for (Follow follow : followerIdList) {
            // 첫번째로 대응하는 followerId가 오겠지.
            // 그 객체에서
            // 그걸로 그 ID에 대응하는 user 객체 생성
            User followerId = follow.getFollowerId();
            // userId로 유저 조회 후 이메일 가져오기
            String followerEmail = followerId.getEmail();
            //  EmailList에 add
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
        // sql 쿼리 결과를 조회해서 받아오는 식이기 때문에 List로 받아올 수 있음.
        List<Follow> followingIdList = followRepository.findByFollowerId(me);

        // id에 맞는 email을 각각 뽑아와서 리스트로 넣어야 하니, sql 쿼리 조회 결과를 하나씩 리스트에 넣는 느낌
        // 따라서 결과를 List로 받는 게 아닌, 만들어 놓고 결과를 하나씩 집어넣는다.
        List<FollowingResponseDto> followingEmailList = new ArrayList<>();


        for(Follow follow : followingIdList) {
            // 첫번째로 대응하는 followingId가 오겠지.
            // 그걸로 그 ID에 대응하는 user 객체 생성
            User followingId = follow.getFollowingId();
            // 생성한 객체에서 이메일만 뽑아서
            String email = followingId.getEmail();
            //  EmailList에 add
            followingEmailList.add(new FollowingResponseDto(email));
        }
        // 이제 그 list를 리턴시키면 마무리

        return followingEmailList;

    }
    // 나중에 친구 게시물 보기 등의 기능 또한 고려...
    // private final BoardRepository boardRepository;

}
