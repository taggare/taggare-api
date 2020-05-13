package com.sns.server.security.handlers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.server.account.Account;
import com.sns.server.security.AccountContext;
import com.sns.server.security.JwtFactory;
import com.sns.server.security.tokens.PostAuthorizationToken;
import com.sns.server.token.Token;
import com.sns.server.token.TokenDto;
import com.sns.server.token.TokenRepository;
import com.sns.server.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);

    private JwtFactory jwtFactory;
    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

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

        Account account = context.getAccount();

        Token tokenByAccount = tokenService.findTokenByAccount(account.getId());
        if (tokenByAccount == null) {
            // 토큰이 존재하지 않으면 생성한다.
            log.info("토큰이 존재하지 않으면 생성한다.");
            String accessToken = jwtFactory.generateAccessToken(context);
            String refreshToken = jwtFactory.generateRefreshToken(context);

            Token createdToken = Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .account(account)
                    .build();
            tokenRepository.save(createdToken);
            processResponse(res, writeDto(accessToken, refreshToken));
            return;
        }

        String accessToken = tokenByAccount.getAccessToken();
        String refreshToken = tokenByAccount.getRefreshToken();

        boolean isValidAccessToken = jwtFactory.verifyToken(accessToken);
        if(isValidAccessToken) {
            // 유효한 Access token이다.
            log.info("유효한 Access token이다");
            processResponse(res, writeDto(accessToken, refreshToken));
            return;
        }

        boolean isValidRefreshToken = jwtFactory.verifyToken(refreshToken);
        if(isValidRefreshToken) {
            // Access Token이 만료 되었지만, refresh 토큰이 유효하므로 Access Token을 재발급한다.
            log.info("Access Token이 만료 되었지만, refresh 토큰이 유효하므로 Access Token을 재발급한다.");
            String newAccessToken = jwtFactory.generateAccessToken(context);
            tokenByAccount.setAccessToken(newAccessToken);
            tokenRepository.save(tokenByAccount);
            processResponse(res, writeDto(newAccessToken, refreshToken));
            return;
        }

        // Access && Refresh 토큰 모두가 유효하지 않으므로 삭제처리
        tokenByAccount.setDeleted(LocalDateTime.now());
        tokenRepository.save(tokenByAccount);
        throw new JWTVerificationException("토큰이 만료되었습니다. 로그인을 다시해주세요.");
    }

    private com.sns.server.token.TokenDto writeDto(String accessToken, String refreshToken) {
        return new com.sns.server.token.TokenDto(accessToken, refreshToken);
    }

    private void processResponse(HttpServletResponse res, TokenDto tokenDto) throws JsonProcessingException, IOException {
        res.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(tokenDto));
    }
}
