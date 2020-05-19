package com.sns.server.security.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);

    /**
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
    }
}
