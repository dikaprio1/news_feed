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



#  📝 API 명세서

- 로그인
POST /api/auth/login

- 게시글 작성
POST /api/posts

- 게시글 전체 조회
GET /api/posts

- 게시글 삭제
DELETE /api/posts/{id}


#  📝 API 예외명세서


