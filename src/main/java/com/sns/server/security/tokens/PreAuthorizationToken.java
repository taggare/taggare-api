package com.sns.server.security.tokens;

import com.sns.server.account.SocialLoginDto;
import com.sns.server.account.SocialProviders;
import com.sns.server.login.FormLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    private PreAuthorizationToken(SocialProviders socialProvider, SocialLoginDto socialLoginDto) {
        super(socialProvider, socialLoginDto);
    }

    public PreAuthorizationToken(SocialLoginDto socialLoginDto) {
        this(socialLoginDto.getProvider(), socialLoginDto);
    }

    public PreAuthorizationToken(FormLoginDto formLoginDto) {
        this(formLoginDto.getEmail(), formLoginDto.getPassword());
    }

    public String getUsername() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
