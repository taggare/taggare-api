package com.sns.server.account;

import com.sns.server.common.TaggareException;
import com.sns.server.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account create(AccountDto.Create accountDto) {
        try {
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
        } catch(Exception e) {
            throw new TaggareException(ErrorCode.NOT_FOUND_USER, e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Optional<Account> isExistAccount(Long id) {
            return Optional.ofNullable(accountRepository.findById(id)
                    .orElseThrow(() -> new TaggareException(ErrorCode.NOT_FOUND_USER)));
    }

    @Transactional
    public Account update(Long id, AccountDto.Update accountDto) {
        Optional<Account> optionalAccount = isExistAccount(id);
        return optionalAccount.map(account -> {
            account.setEmail(accountDto.getEmail());
            account.setFirstName(accountDto.getFirstName());
            account.setLastName(accountDto.getLastName());
            account.setPassword(accountDto.getPassword());
            account.setTel(accountDto.getTel());
            return accountRepository.save(account);
        }).orElseThrow(() -> new TaggareException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Transactional
    public Account delete(Long id) {
        Optional<Account> optionalAccount = isExistAccount(id);
        return optionalAccount.map(account -> {
            account.setDeleted(LocalDateTime.now());
            return accountRepository.save(account);
        }).orElseThrow(() -> new TaggareException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
