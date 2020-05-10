package com.sns.server.security.providers;

import com.sns.server.account.*;
import com.sns.server.enums.Gender;
import com.sns.server.enums.UserRole;
import com.sns.server.security.AccountContext;
import com.sns.server.security.services.specification.SocialFetchService;
import com.sns.server.security.social.SocialUserProperty;
import com.sns.server.security.tokens.PostAuthorizationToken;
import com.sns.server.security.tokens.SocialPreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final SocialFetchService socialFetchService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        SocialLoginDto socialLoginDto = token.getSocialDto();

        return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(getAccount(socialLoginDto)));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private Account getAccount(SocialLoginDto socialLoginDto) {
        SocialUserProperty socialUserProperty = socialFetchService.getSocialUserInfo(socialLoginDto);

        String userId = socialUserProperty.getUserId();
        SocialProviders socialProvider = socialLoginDto.getProvider();

        // DB에 조회를 하고 유저가 없으면 새로운 account를 생성한다.
        Account account = accountRepository.findBySocialIdAndSocialProvider(Long.valueOf(userId), socialProvider);
        if (account == null) {
            return AccountDto.Create.builder()
                    .email(null)
                    .firstName(null)
                    .lastName(null)
                    .password(null)
                    .gender(Gender.UNDEFINED)
                    .birth(null)
                    .tel(null)
                    .userRole(UserRole.USER)
                    .socialProvider(socialProvider)
                    .socialId(Long.valueOf(socialUserProperty.getUserId()))
                    .profileHref(socialUserProperty.getProfileHref())
                    .build()
                    .convert();
        }
        return account;
    }
}
