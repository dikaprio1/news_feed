package com.example.news_feed.follow.entity;

import com.example.news_feed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "follow", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower", "following"})
})
@EntityListeners(AuditingEntityListener.class)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName ="id", name = "follower", nullable = false)
    // 한 명의 유저를 여러명이 팔로우 할 수 있다....
    private User followerId;

    @ManyToOne
    @JoinColumn(referencedColumnName ="id", name = "following", nullable = false)
    // 한 명의 유저가 여러 아이디를 팔로우할 수 있다?
    private User followingId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Follow() {

    }

    public Follow(User followerId,User followingId){
        this.followerId=followerId;
        this.followingId=followingId;
    }

}
