package com.sns.server.login;

import com.sns.server.account.Account;
import com.sns.server.account.AccountService;
import com.sns.server.exceptions.account.AccountNotAuthorizedException;
import com.sns.server.exceptions.account.AccountNotFoundException;
import com.sns.server.security.AccountContext;
import com.sns.server.security.tokens.PostAuthorizationToken;
import com.sns.server.security.tokens.PreAuthorizationToken;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private AccountService accountService;
    private PasswordEncoder passwordEncoder;

    LoginService(@Lazy AccountService accountService, @Lazy PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isCorrectPassword(String password, Account account) {
        return passwordEncoder.matches(password, account.getPassword());
    }

    @Transactional
    public PostAuthorizationToken authenticate(Authentication authentication) {

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

}
