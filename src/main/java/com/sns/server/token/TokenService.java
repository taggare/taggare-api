package com.sns.server.token;

import com.sns.server.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    public Token findTokenByAccount(Long id) {
        return tokenRepository.findTokenByAccount(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Account findAccountByAccessToken(String accessToken) {
        Optional<Token> token = tokenRepository.findAccountByAccessToken(accessToken);
        if(!token.isPresent()) {
            return null;
        }
        return token.get().getAccount();
    }
}
