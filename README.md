# spring-security-jwt

## 🚀 프로젝트 개요
이 프로젝트는 Spring Security와 JWT(JSON Web Token)를 활용하여  
간단한 인증 및 권한 부여 기능을 구현한 예제 애플리케이션입니다.  
RESTful API 형태로 제공되며, 사용자 회원가입, 로그인, 그리고 역할 기반의 접근 제어를 구현했습니다.

## ✨ 주요 특징
*   **JWT 기반 인증**: Spring Security의 인증 절차에 JWT를 통합하여 Stateless한 인증 방식을 제공합니다.
    *   `JwtAuthenticationFilter`: HTTP 요청 헤더에서 JWT를 추출하고 인증을 처리합니다.
    *   `JwtTokenProvider`: JWT 토큰의 생성, 유효성 검사, 클레임 추출 등 토큰 관련 핵심 로직을 담당합니다.
    *   `JwtExceptionFilter`: JWT 관련 예외(만료, 유효하지 않은 토큰 등)를 처리하여 클라이언트에게 적절한 오류 응답을 제공합니다.
*   **회원가입 및 로그인 API**:
    *   `/register` (POST): 새로운 사용자를 등록합니다. `username`, `password`, `admin`(boolean)을 JSON 형식의 HTTP POST Body로 받습니다.
    *   `/login` (POST): 사용자 인증을 수행하고, 성공 시 `Access Token`과 `Refresh Token`을 반환합니다.
*   **역할 기반 접근 제어**: Spring Security의 권한 계층(`RoleHierarchy`)을 활용하여 세분화된 접근 제어를 구현합니다.
    *   `/onlyadmin` 엔드포인트: `ADMIN` 권한을 가진 사용자만 접근할 수 있습니다.
    *   `/allowall` 엔드포인트: `USER` 권한을 가진 사용자만 접근할 수 있습니다.
    *   `ADMIN` 권한은 `USER` 권한을 상속받아, `ADMIN` 사용자는 `USER` 권한이 필요한 리소스에도 접근할 수 있습니다.
*   **JPA를 이용한 사용자 정보 관리**: Spring Security의 `UserDetailsService` 구현체(`UserDetailsServiceImpl`)에서 JPA(`UserRepository`)를 통해 사용자 정보를 로드합니다.
*   **H2 In-Memory Database**: 예제 애플리케이션의 편의를 위해 H2 인메모리 데이터베이스를 사용합니다. 별도의 데이터베이스 설정 없이 즉시 실행 가능합니다.
*   **DB 설정**: 데이터베이스 연결 정보(username, password 등)는 `src/main/resources/application.yml` 파일에서 수정할 수 있습니다.
*   **동적 JWT Secret**: JWT 토큰 생성에 사용되는 Secret 키는 애플리케이션 시작 시마다 랜덤으로 생성됩니다, 실행 시 로그에서 확인할 수 있습니다.
*   **일관된 API 응답 형식**: 모든 API 응답은 `ApiResponse` DTO를 사용하여 성공 및 실패 응답을 일관된 JSON 형식으로 제공합니다. `GlobalExceptionHandler`를 통해 전역적인 예외 처리를 담당합니다.

## 🛠️ 기술 스택
*   **Spring Boot**
*   **Spring Security**
*   **JWT (JSON Web Token)**
*   **Spring Data JPA**
*   **H2 Database**
*   **Gradle**
*   **Lombok**
*   **ModelMapper**

## 📂 프로젝트 구조
```
src/main/java/dev/friox/springsecurityjwt/
├── config/             # Spring Security 및 ModelMapper 설정
├── controller/         # REST API 엔드포인트 정의 (회원가입, 로그인, 권한 테스트)
├── exception/          # 커스텀 예외 및 전역 예외 핸들러
├── model/
│   ├── dto/            # 요청 및 응답 데이터 전송 객체 (DTO)
│   └── entity/         # JPA 엔티티 (User, Role)
├── repository/         # JPA 데이터베이스 인터페이스
├── security/           # Spring Security 관련 설정 및 구현
│   └── jwt/            # JWT 토큰 생성, 검증, 필터 등 JWT 관련 핵심 로직
└── service/            # 비즈니스 로직 구현 (UserService)