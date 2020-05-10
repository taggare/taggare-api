package com.sns.server.security.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.server.security.AccountContext;
import com.sns.server.security.JwtFactory;
import com.sns.server.security.dtos.TokenDto;
import com.sns.server.security.tokens.PostAuthorizationToken;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtFactory jwtFactory;
    private ObjectMapper objectMapper;

    FormLoginAuthenticationSuccessHandler(@Lazy JwtFactory jwtFactory, @Lazy ObjectMapper objectMapper) {
        this.jwtFactory = jwtFactory;
        this.objectMapper = objectMapper;
    }

    /**
     * {@link com.sns.server.security.tokens.PostAuthorizationToken}이 provider에서 넘어오는 인증 토큰 값을
     * AccountContext에서 유저의 객체가 가지고 있는 권한정보를 JWT factory가 웹토큰을 생성.
     *
     * @param req
     * @param res
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
        PostAuthorizationToken token = (PostAuthorizationToken) auth;
        AccountContext context = (AccountContext) token.getPrincipal();

        String generatedToken = jwtFactory.generateToken(context);
        processResponse(res,writeDto(generatedToken));
    }

    private TokenDto writeDto(String token) {
        return new TokenDto(token);
    }

    private void processResponse(HttpServletResponse res, TokenDto tokenDto) throws JsonProcessingException, IOException {
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(tokenDto));
    }
}
