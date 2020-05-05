package com.sns.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Log
public class JwtFactory {

    private static String signKey = "jwttest";

    public String generateToken(AccountContext context) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("preciouStar")
                    .withClaim("USER_EMAIL", context.getAccount().getEmail())
                    .withClaim("USER_ROLE", context.getAccount().getUserRole().getRoleName())
                    .sign(generateAlgorithm());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        return Algorithm.HMAC256(signKey);
    }
}
