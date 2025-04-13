# News Feed API

- Spring Boot 기반 뉴스피드 프로젝트입니다.  
- 회원가입/로그인, 게시글 작성/조회/삭제, 팔로우 기능 등을 제공합니다.


## 📌 프로젝트 소개

### 🐾 프로젝트명: **냠냠일기**

친구들과 서로 먹은 것을 공유하며 소통하는 SNS 서비스입니다.

---

## 👥 역할 분담

| 기능 | 담당자 |
|------|--------|
| 회원 CRUD | 이진아 |
| 게시글 CRUD | 이은혜(조회, 수정), 김민준(작성, 삭제) |
| 로그인 | 이진아, 김석진 |
| 친구 관리 | 박민규, 김석진 |



## 🛠️ 개발 환경

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Gradle
- Lombok
- Jakarta Validation
- Postman (테스트용)



## 📝 ERD
![뉴스피드 ERD.PNG](%EB%89%B4%EC%8A%A4%ED%94%BC%EB%93%9C%20ERD.PNG)


#  📝 API 명세서
---

## 🧑 유저 관련

| 기능         | HTTP Method | URL                        | Path Variable | Request Param | Request Body 예시 | Response 예시 | 상태 |
|--------------|--------------|-----------------------------|----------------|----------------|-------------------|----------------|------|
| 회원가입      | POST         | /api/auth/signup            | X              | X              | `{ "username": "홍길동", "email": "hong@test.com", "password": "Test@1234", "gender": "MALE", "age": 25 }` | 201 Created `{ "message": "회원가입 성공", ... }` 
| 로그인        | POST         | /api/auth/login             | X              | X              | `{ "email": "hong@test.com", "password": "Test@1234" }` | 200 OK `{ "message": "로그인 성공" }` 
| 프로필 조회   | GET          | /api/users/{id}             | id(Long)       | X              | X                 | 200 OK `{ "username": "홍길동", ... }` 
| 이름 수정     | PATCH        | /api/users/{id}/name        | id(Long)       | X              | `{ "username": "김민수" }` | 200 OK<br>`{ "message": "프로필 수정 완료" }` 
| 비밀번호 수정 | PATCH        | /api/users/{id}/password    | id(Long)       | X              | `{ "oldPassword": "Test@1234", "newPassword": "Test@1235" }` | 200 OK `{ "message": "프로필 수정 완료" }`
| 회원 탈퇴     | DELETE       | /api/users/{id}             | id(Long)       | X              | `{ "email": "hong@test.com", "password": "Test@1234" }` | 200 OK `{ "message": "회원탈퇴 성공", "deleted_at": "2025-00-00" }`

---

## 📝 게시글 관련

| 기능         | HTTP Method | URL                      | Path Variable | Request Param | Request Body 예시 | Response 예시 | 상태 |
|--------------|--------------|---------------------------|----------------|----------------|-------------------|----------------|------|
| 게시글 작성   | POST         | /api/posts                | X              | X              | `{ "title": "제목", "content": "내용", "image": "img.url" }` | 200 OK `{ "id": 1, "title": "제목", ... }` |
| 게시글 조회   | GET          | /api/posts/{id}           | id(Long)       | X              | X                 | 200 OK `{ "id": 1, "title": "제목", ... }` 
| 게시글 목록   | GET          | /api/posts                | X              | X              | X                 | 200 OK `[{ "id": 1, "title": "제목", ... }]` 
| 뉴스피드 조회 | GET          | /api/posts/newsfeed       | X              | `?page=`       | X                 | 200 OK `[{ "id": 1, "title": "제목", ... }]` 
| 게시글 수정   | PUT          | /api/posts/{id}           | id(Long)       | X              | `{ "title": "수정된 제목", "content": "수정된 내용", "imageUrl": "img.url" }` | 200 OK `{ "message": "수정 성공" }` 
| 게시글 삭제   | DELETE       | /api/posts/{id}           | id(Long)       | X              | X                 | 200 OK `{ "message": "삭제 성공" }`

---

## 👥 팔로우 관련

| 기능         | HTTP Method | URL                          | Path Variable | Request Param | Request Body 예시 | Response 예시 | 상태 |
|--------------|--------------|-------------------------------|----------------|----------------|-------------------|----------------|------|
| 팔로우 하기   | POST         | /api/follow/{id}              | id(Long)       | X              | `{ "targetId": 3 }` | 200 OK `{ "message": "팔로우 요청 완료" }` 
| 팔로워 조회   | GET          | /api/follow/followerList      | X              | X              | X                 | 200 OK `[{ "id": 2, "username": "홍길동" }]`
| 팔로잉 조회   | GET          | /api/follow/followingList     | X              | X              | X                 | 200 O K`[{ "id": 2, "username": "홍길동" }]` 
| 언팔로우      | DELETE       | /api/follow/delete            | X              | X              | `{ "targetId": 3 }` | 200 OK `{ "message": "언팔로우 완료", "deleted_at": "2025-00-00" }`


#  📝 API 예외명세서




