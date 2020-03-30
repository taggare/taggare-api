package com.sns.server.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account createUser(AccountDto.Create accountDto) {
        Account account = Account.builder()
                .email(accountDto.getEmail())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .password(accountDto.getPassword())
                .gender(accountDto.getGender())
                .birth(accountDto.getBirth())
                .tel(accountDto.getTel())
                .build();

        return accountRepository.save(account);
    }
}
