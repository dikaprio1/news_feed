package com.example.news_feed.follow.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FollowerResponseDto {

    // 그냥 followers string 객체로 email 받아오는 식으로 하는게 맞는것같은데ㅔ
    // service단에서 List로 받아오고

    private final String email;

}
