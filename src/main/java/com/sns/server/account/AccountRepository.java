package com.sns.server.account;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Scope("prototype")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
