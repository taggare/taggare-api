package com.sns.server.security.tokens;

import com.sns.server.account.SocialLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SocialPreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public SocialPreAuthorizationToken(SocialLoginDto socialLoginDto) {
        super(socialLoginDto.getProvider(), socialLoginDto);
    }

    public SocialLoginDto getSocialDto() {
        return (SocialLoginDto) super.getCredentials();
    }
}
