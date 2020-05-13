package com.sns.server.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.server.login.FormLoginDto;
import com.sns.server.security.tokens.PreAuthorizationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter 클래스를 @Component 클래스로 빈으로 등록한다고 해서, Autowired 받을 수 있는게 없기 때문에
 * SecurityConfig에서 직접 구현해야하므로 빈으로 구현하지 않는다.
 */
public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public FormLoginFilter(String defaultUrl, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler) {
        super(defaultUrl);
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        FormLoginDto formLoginDto = new ObjectMapper().readValue(req.getReader(), FormLoginDto.class);
        PreAuthorizationToken preAuthorizationToken = new PreAuthorizationToken(formLoginDto);
        return super.getAuthenticationManager().authenticate(preAuthorizationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(req, res, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        AuthenticationFailureHandler handler = (req, res, exception) -> {
            logger.info(exception.getMessage());
        };
        handler.onAuthenticationFailure(request, response, failed);
    }
}
