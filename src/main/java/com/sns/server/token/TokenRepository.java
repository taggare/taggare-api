package com.sns.server.token;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    @Query(value = "SELECT token from Token token LEFT JOIN FETCH token.account account WHERE account.id = ?1")
    Optional<Token> findTokenByAccount(Long id);

    @Query(value = "SELECT token from Token token WHERE token.accessToken = ?1")
    Optional<Token> findAccountByAccessToken(String accessToken);
}
