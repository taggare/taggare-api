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
        // TODO: 비밀번호 최소 SHA256 암호화
        Optional<Account> account = accountRepository.findByEmail(accountDto.getEmail());
        if(account.isPresent()) {
            throw new EmailConflictException();
        }
        return accountRepository.save(accountDto.convert());
    }

    @Transactional
    public Account update(Long id, AccountDto.Update accountDto) {
        // TODO: 비밀번호 변경을 분리
        // TODO: 회원정보 변경 분리(firstName, lastName, tel)
        get(id).setFirstName(accountDto.getFirstName());
        get(id).setLastName(accountDto.getLastName());
        get(id).setPassword(accountDto.getPassword());
        get(id).setTel(accountDto.getTel());

        return  get(id);
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
