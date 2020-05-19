package com.sns.server.security.filters;

import com.sns.server.security.handlers.JwtAuthenticationFailureHandler;
import com.sns.server.security.tokens.JwtPreProcessingToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private JwtAuthenticationFailureHandler failureHandler;

    protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {

        JwtPreProcessingToken token = null;
        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        chain.doFilter(req, res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        // 인증이 실패했기 때문에 실패한 쓰레드의 시큐리티 context를 제거해야한다.
        SecurityContextHolder.clearContext();
        this.unsuccessfulAuthentication(req, res, failed);
    }
}
