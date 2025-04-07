package com.example.news_feed.user.entity;

import baseentity.BaseEntity;
import common.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "이메일이 올바른 형식이 아닙니다.")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    // Enum을 Entity의 자료형으로 쓸 때, 자료형을 정해주기 위해 쓰는 어노테이션
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
}