package com.example.news_feed.user.entity;

import com.example.news_feed.baseentity.BaseEntity;
import com.example.news_feed.common.Gender;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    // email 유효성 검사
    // @Pattern <- 정규식 표현하는 어노테이션을 통해 엔티티에서 진행 -> dto에서 진행하기로! 아래코드 추후 삭제
    // @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "이메일이 올바른 형식이 아닙니다.")
    private String email;

    @Column(nullable = false)
    private String password;

    // Enum을 Entity의 자료형으로 쓸 때, 자료형을 정해주기 위해 쓰는 어노테이션
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private Integer age;

    public User() {
    }

    public User(String name, String email, String password, Gender gender, Integer age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name){
        this.name = name;
    }

}