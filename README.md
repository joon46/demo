# 🛡️ Spring Boot REST API 기반 시큐어 코딩 게시판
---

## 📌 프로젝트 핵심 요약
* **프로젝트 개요:** Spring Boot(REST API)와 Vanilla JS 기반 게시판을 구축하고, 주요 웹 보안 취약점을 단계별로 진단·조치한 시큐어 코딩 프로젝트입니다.
* **[1단계] Stored XSS 방어:** DOM 렌더링 시 `innerHTML` 대신 `textContent` API를 적용하여 악성 스크립트(`<img>` 이벤트 핸들러 등) 실행을 원천 차단했습니다.
* **[2단계] 파라미터 변조 방어:** 백엔드 Entity/DTO에 `@NotBlank`, `@Size`와 `@Valid` 검증을 적용해 비정상·공백 입력을 `400 Bad Request`로 사전 차단했습니다.
* **[3단계] 권한 검증 및 데이터 보호:** `BCrypt` 암호화 비밀번호 검증으로 비인가 삭제(IDOR) 시도를 `403 Forbidden`으로 차단하고, `@JsonProperty(WRITE_ONLY)` 및 Body 전송을 통해 민감정보 노출을 방지했습니다.

---

## 🛠 기술 스택 (Tech Stack)
* **Backend:** Java, Spring Boot, Spring Security, Spring Data JPA, Bean Validation
* **Frontend:** HTML5, CSS3, Vanilla JavaScript (Fetch API)
* **Database:** H2 Database (In-Memory)
* **Tools:** VS Code, Maven, Thunder Client

---

## 🏗 프로젝트 구조 (Architecture)
```text
src/main/
 ├── java/com/example/demo/
 │    ├── config/
 │    │    └── SecurityConfig.java      # Spring Security 필터 체인 및 BCryptPasswordEncoder 등록
 │    ├── controller/
 │    │    └── BoardController.java     # REST API 엔드포인트 (/api/boards) 및 @Valid 검증
 │    ├── domain/
 │    │    └── Board.java               # JPA Entity, Validation 규칙 및 @JsonProperty 설정
 │    ├── repository/
 │    │    └── BoardRepository.java     # Spring Data JPA 기반 DB 접근 계층
 │    └── service/
 │         └── BoardService.java        # 비즈니스 로직 및 비밀번호 해시 암호화/일치 검증
 └── resources/
      └── static/
           └── index.html               # CSR 기반 게시판 UI 및 XSS 방어 렌더링
```

---

## 🔍 단계별 보안 취약점 진단 및 조치 내역

| 단계 | 취약점 유형 | 진단 내용 (Before) | 보안 조치 (After) | 검증 결과 |
| :--- | :--- | :--- | :--- | :--- |
| **1단계** | **Stored XSS** | `innerHTML`로 게시글을 렌더링하여 본문 내 악성 스크립트 태그가 브라우저에서 실행됨 | `document.createElement` 및 `textContent` API를 사용해 입력값을 순수 문자열로 처리 | 스크립트 실행 차단 및 텍스트 그대로 출력 확인 |
| **2단계** | **파라미터 변조** | API 직접 호출 시 공백이나 과도한 길이의 문자열이 검증 없이 DB에 저장됨 | DTO/Entity에 `@NotBlank`, `@Size` 적용 및 Controller `@Valid` 활성화 | 비정상 요청 시 `400 Bad Request` 반환 및 차단 |
| **3단계** | **인가 누락 (IDOR) & 민감정보 노출** | 별도의 인증 없이 타인의 글 삭제 가능, 평문 비밀번호 저장 및 응답 노출 위험 | `BCrypt` 단방향 해시 암호화 저장, 삭제 시 Request Body로 비밀번호 대조, `@JsonProperty(WRITE_ONLY)` 적용 | 비밀번호 불일치 시 `403 Forbidden` 차단, 목록 조회 응답에서 비밀번호 필드 은닉 |

---

## 🔗 주요 API 명세 (Endpoints)

| Method | Endpoint | 설명 | 주요 보안 적용 사항 |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/boards` | 전체 게시글 목록 조회 | `@JsonProperty(WRITE_ONLY)`로 비밀번호 해시값 응답 제외 |
| `GET` | `/api/boards/{id}` | 특정 게시글 상세 조회 | JPA 파라미터 바인딩을 통한 SQL Injection 예방 |
| `POST` | `/api/boards` | 새 게시글 등록 | `@Valid` 입력값 검증 및 `BCrypt` 비밀번호 암호화 저장 |
| `DELETE` | `/api/boards/{id}` | 게시글 삭제 | Request Body 기반 비밀번호 검증 및 불일치 시 `403 Forbidden` 반환 |

---

