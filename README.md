# sns-server

-   [x] 서버 response 구조 정형화
-   [x] 에러 response 구조 정형화
-   [x] 서버 에러 발생시 클라이언트에 서버에러가 아닌 커스텀 예외 메시지 전달
-   [x] ExceptionHandler를 통한 예외처리
-   [x] HashTag, History, Like, Post 관계 설정
-   [x] Heroku 서버 생성
-   [x] 회원가입시 Email 중복체크 및 예외처리
-   [ ] Spring Security 코어 파악
-   [x] Reply domain 생성  


### JWT token request verification
* Post.http -> Login.http -> loggedin-request.http  


### JWT 토큰 테스트
회원 가입 -> 로그인 요청 -> 토큰 생성 및 클라이언트에 전달 -> 헤더에 토큰이 있는지 검증답 성 -> JWT Claim Set 유저 아이디 응답(유저 이메일)