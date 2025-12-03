# JSP Board

JSP, Servlet, MyBatis를 활용한 MVC 패턴 게시판 프로젝트  
자세한 내용은 notion을 참고해주세요  
https://www.notion.so/jsp_workspace-2b72afccb73680a89592e95e1a8fe517?source=copy_link

## 기술 스택

| 구분 | 기술 |
|------|------|
| Frontend | JSP, JSTL, HTML/CSS, JavaScript |
| Backend | Servlet 4.0, Java 17 |
| Database | MySQL 8.0, MyBatis 3.5 |
| Server | Apache Tomcat 9.0 |
| Build | Maven |
| IDE | IntelliJ IDEA |
| OS | macOS |

## 주요 기능

### 회원
- 회원가입 / 로그인 / 로그아웃
- 회원정보 수정
- 세션 기반 인증

### 게시판
- 게시글 CRUD (작성, 조회, 수정, 삭제)
- 페이징 처리
- 검색 기능 (제목, 작성자, 내용)

### 댓글
- 댓글 CRUD
- 비동기 처리 (Fetch API)
- 작성자 본인만 수정/삭제 가능

### 파일
- 파일 업로드 / 다운로드
- 이미지 썸네일 자동 생성 (Thumbnailator)

## 프로젝트 구조
```
src/main/java
├── controller/     # Servlet 컨트롤러
├── service/        # 비즈니스 로직
├── repository/     # Data Access Object
├── domain/         # VO 클래스
├── handler/        # handler 클래스
└── orm/         # DB 연결 설정

src/main/webapp
├── WEB-INF/views/  # JSP 파일
├── resources/      # CSS, JS, 이미지
└── upload/         # 업로드 파일 저장
```

## 구현 화면

### 메인 페이지
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 19 57" src="https://github.com/user-attachments/assets/a471fc9e-dc14-48bf-8cb9-78e1c5e88ed4" />

### 게시판 페이지
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 20 07" src="https://github.com/user-attachments/assets/58e95206-3f0a-404a-8eab-a04cc163bdee" />

### 게시판 글쓰기 페이지
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 25 08" src="https://github.com/user-attachments/assets/cbe8518e-4072-42b1-a8ff-8593397fae3b" />

### 게시판 상세보기 페이지
- 내가 작성한 게시물
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 22 01" src="https://github.com/user-attachments/assets/33f3670c-8098-4507-8b18-3fed28c5b522" />
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 21 53" src="https://github.com/user-attachments/assets/87264e8b-afac-40cb-8b04-58e9222f55ed" />

- 다른 유저가 작성한 게시물
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 22 06" src="https://github.com/user-attachments/assets/b04e59aa-bd6a-4f95-9841-1d40e44fc3c5" />

### 수정 페이지
- 게시글
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 28 14" src="https://github.com/user-attachments/assets/c0b4a3ce-8430-4385-be40-3ba3eafc2648" />

- 댓글
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 28 57" src="https://github.com/user-attachments/assets/dc22292a-5d8e-4110-b086-8c8ceaf7eefe" />

### 회원가입 페이지
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 21 16" src="https://github.com/user-attachments/assets/9da3ed87-65c8-48ed-8679-5b29db562a21" />

### 로그인 후 화면
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 21 26" src="https://github.com/user-attachments/assets/2dd4a83e-f573-40dd-a79c-04ba50641dbe" />

### 회원정보 수정 페이지
<img width="1512" height="949" alt="스크린샷 2025-12-03 오후 12 21 33" src="https://github.com/user-attachments/assets/68077ab9-9b8b-4cda-9051-b7719547ce88" />







