package com.sns.server.account;

import com.sns.server.enums.Gender;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@FixMethodOrder(MethodSorters.JVM)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void 사용자_계정_생성() {
        AccountDto.Create createAccountDto = AccountDto.Create.builder()
                .email("test@github.com")
                .firstName("test")
                .lastName("park")
                .password("test1234")
                .gender(Gender.MALE)
                .birth("2020-01-01")
                .tel("010-0000-0000")
                .build();

        accountService.create(createAccountDto);
        assertThat(accountService.get(1L).getEmail().equals(createAccountDto.getEmail()));
    }

    @Test
    public void 사용자_계정_조회() {
        assertThat(accountService.get(1L).getEmail().equals("test@github.com"));
    }

    @Test
    public void 사용자_계정_변경() {
        AccountDto.Update updateAccount = AccountDto.Update.builder()
                .firstName("test2")
                .lastName("park2")
                .password("test1234")
                .tel("010-1111-1111")
                .build();

        accountService.update(1L, updateAccount);
        assertThat(accountService.get(1L).getEmail().equals(updateAccount.getEmail()));
    }

    @Test
    public void 사용자_계정_탈퇴() {
        Optional<Account> deleteAccount = Optional.of(accountService.delete(1L));
        assertThat(deleteAccount.isPresent() == false);
    }

}
