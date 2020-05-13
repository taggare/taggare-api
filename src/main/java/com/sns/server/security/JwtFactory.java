package com.sns.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    public static final long JWT_ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 6;   // 6시간만 토큰 유효
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 60;   // 60일 토큰 유효

    @Value("${jwt.signKey}")
    private String signKey;

    @Value("${jwt.issuer}")
    private String issuer;

    public String generateAccessToken(AccountContext context) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("USER_EMAIL", context.getAccount().getEmail())
                .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                .withExpiresAt(new Date(JWT_ACCESS_TOKEN_VALIDITY))
                .sign(generateAlgorithm());
    }

    public String generateRefreshToken(AccountContext context) {
        return JWT.create()
                .withIssuer(issuer)
                .withClaim("USER_EMAIL", context.getAccount().getEmail())
                .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                .withExpiresAt(new Date(JWT_REFRESH_TOKEN_VALIDITY))
                .sign(generateAlgorithm());
    }

//    public String generateAccessToken(AccountContext context) {
//        return generateToken(context, JWT_ACCESS_TOKEN_VALIDITY);
//    }
//
//    public String generateRefreshToken(AccountContext context) {
//        return generateToken(context, JWT_REFRESH_TOKEN_VALIDITY);
//    }

    private String generateToken(AccountContext context, Long tokenValidity) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("USER_EMAIL", context.getAccount().getEmail())
                    .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                    .withExpiresAt(new Date(System.currentTimeMillis() + tokenValidity))
                    .sign(generateAlgorithm());
        } catch (JWTCreationException e) {
            log.error(e.getMessage());
        }
        return token;
    }

    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(signKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            return true;
        } catch(JWTVerificationException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(signKey);
    }
}
