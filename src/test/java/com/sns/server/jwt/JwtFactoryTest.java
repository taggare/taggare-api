package com.sns.server.jwt;

import com.sns.server.account.Account;
import com.sns.server.enums.Gender;
import com.sns.server.enums.UserRole;
import com.sns.server.security.AccountContext;
import com.sns.server.security.JwtFactory;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log
public class JwtFactoryTest {

    private AccountContext accountContext;

    @Autowired
    private JwtFactory jwtFactory;

    @Before
    public void setUp() {
        Account account = Account.builder()
                .email("test@github.com")
                .firstName("test")
                .lastName("park")
                .password("test1234")
                .gender(Gender.MALE)
                .birth("2020-01-01")
                .tel("010-0000-0000")
                .userRole(UserRole.USER)
                .build();
        this.accountContext = AccountContext.fromAccountModel(account);
    }

    @Test
    public void JWT_토큰_생성() {
        log.info(jwtFactory.generateToken(this.accountContext));
    }
}
