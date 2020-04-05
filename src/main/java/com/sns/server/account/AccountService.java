package com.sns.server.account;

import com.sns.server.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account create(AccountDto.Create accountDto) {
        // TODO: 이메일 중복 체크
        // TODO: DB 에러가 아닌 response error 감싸라
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

    @Transactional
    public Account update(Long id, AccountDto.Update accountDto) {
        return get(id).builder()
                .email(accountDto.getEmail())
                .firstName(accountDto.getFirstName())
                .lastName(accountDto.getLastName())
                .password(accountDto.getPassword())
                .tel(accountDto.getTel())
                .build();
    }

    @Transactional
    public Account delete(Long id) {
        return get(id).builder()
                .deleted(LocalDateTime.now())
                .build();
    }

    @Transactional(readOnly = true)
    public Account get(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
    }
}
