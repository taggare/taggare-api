package com.sns.server.account;

import com.sns.server.exceptions.account.AccountNotFoundException;
import com.sns.server.exceptions.account.EmailConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account create(AccountDto.Create accountDto) {
        Optional<Account> account = accountRepository.findByEmail(accountDto.getEmail());
        if(account.isPresent()) {
            throw new EmailConflictException();
        }
        return accountRepository.save(accountDto.convert());
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
