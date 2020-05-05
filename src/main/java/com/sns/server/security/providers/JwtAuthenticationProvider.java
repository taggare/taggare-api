package com.sns.server.security.providers;

import com.sns.server.security.AccountContext;
import com.sns.server.security.JwtDecoder;
import com.sns.server.security.tokens.JwtPreProcessingToken;
import com.sns.server.security.tokens.PostAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getPrincipal();
        AccountContext context = jwtDecoder.decodeJwt(token);

        return PostAuthorizationToken.getTokenFromAccountContext(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication);
    }
}
