package com.sns.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class JwtDecoder {
    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    /**
     * 유효한 JWT라면 AccountContext를 리턴한다.
     * 유효하지 않은 JWT라면 예외를 던지거나 비어있는 인증객체를 리턴시킨다.
     * @param token
     * @return
     */
    public AccountContext decodeJwt(String token) {
        // TODO 토큰 상세 exception 클래스로 전
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(() -> new NoSuchElementException("유효한 토큰이 아닙니다."));
        String userEmail = decodedJWT.getClaim("USER_EMAIL").asString();
        String role = decodedJWT.getClaim("USER_ROLE").asString();

        return new AccountContext(userEmail,"1234", role);

    }

    private Optional<DecodedJWT> isValidToken(String token) {
        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256("jwttest");
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwt = verifier.verify(token);
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(jwt);
    }
}
