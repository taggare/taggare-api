package com.sns.server.security.providers;

import com.sns.server.account.Account;
import com.sns.server.account.AccountService;
import com.sns.server.exceptions.account.AccountNotAuthorizedException;
import com.sns.server.exceptions.account.AccountNotFoundException;
import com.sns.server.security.AccountContext;
import com.sns.server.security.tokens.PostAuthorizationToken;
import com.sns.server.security.tokens.PreAuthorizationToken;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private AccountService accountService;
    private PasswordEncoder passwordEncoder;

    FormLoginAuthenticationProvider(@Lazy AccountService accountService, @Lazy PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getUserPassword();

        Account account = accountService.findByEmail(username);
        if (account == null) {
            throw new AccountNotFoundException();
        }

        if (isCorrectPassword(password, account)) {
            return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(account));
        }

        throw new AccountNotAuthorizedException();
    }

    /**
     * 사용자 인증전에 provider가 어떤 클래스를 통해 검증할 지 체크한다.
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(password, account.getPassword());
    }
}
