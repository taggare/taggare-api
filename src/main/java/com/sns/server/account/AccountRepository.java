package com.sns.server.account;

import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Scope("prototype")
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);

    Account findBySocialId(Long socialId);
}
