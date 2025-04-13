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

📁 User API
메서드	URL	설명	요청값	응답값
POST	/api/users/signup	회원가입	username, email, password, age, gender	가입 성공 메시지
POST	/api/users/login	로그인	email, password	로그인 성공, 세션 저장
GET	/api/users/{id}	단일 유저 조회	-	id, username, email, age, gender 등
PUT	/api/users/{id}	유저 정보 수정	username, age, gender 등	수정된 유저 정보
DELETE	/api/users/{id}	회원 탈퇴	password 검증	탈퇴 성공 메시지

- 로그인
POST /api/auth/login

- 게시글 작성
POST /api/posts

- 게시글 전체 조회
GET /api/posts

- 게시글 삭제
DELETE /api/posts/{id}





