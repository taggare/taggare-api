package com.sns.server.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.server.account.SocialLoginDto;
import com.sns.server.security.tokens.SocialPreAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    protected SocialLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public SocialLoginFilter(String url, AuthenticationSuccessHandler authenticationSuccessHandler) {
        super(url);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        SocialLoginDto socialLoginDto = objectMapper.readValue(req.getReader(), SocialLoginDto.class);
        return super.getAuthenticationManager().authenticate(new SocialPreAuthorizationToken(socialLoginDto));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(req, res, authResult);
    }
}
