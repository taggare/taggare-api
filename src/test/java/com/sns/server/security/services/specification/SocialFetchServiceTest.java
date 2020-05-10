package com.sns.server.security.services.specification;

import com.sns.server.account.Account;
import com.sns.server.account.SocialLoginDto;
import com.sns.server.account.SocialProviders;
import com.sns.server.enums.Gender;
import com.sns.server.enums.UserRole;
import com.sns.server.security.AccountContext;
import com.sns.server.security.JwtFactory;
import com.sns.server.security.social.SocialUserProperty;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
public class SocialFetchServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SocialFetchServiceTest.class);

    private SocialFetchServiceImpl socialFetchService = new SocialFetchServiceImpl();
    private SocialLoginDto socialLoginDto;

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
                .socialProvider(null)
                .socialId(null)
                .profileHref(null)
                .build();

        this.socialLoginDto = new SocialLoginDto(SocialProviders.KAKAO, jwtFactory.generateToken(this.accountContext));
        logger.info("socialLoginDto:" + this.socialLoginDto);
        this.accountContext = AccountContext.fromAccountModel(account);
    }


//    @Before
//    public void setUp() {
//        this.socialLoginDto = new SocialLoginDto(SocialProviders.KAKAO, "OipgELwyJdyDo0zbdjprIavpD3h6ZE5vgtdKFAo9dRoAAAFx_jO45w");
//    }

    @Test
    public void 카카오_로그인_요청() {
        SocialUserProperty property = socialFetchService.getSocialUserInfo(this.socialLoginDto);
        logger.info("getEmail:" + property.getEmail());
        assertThat(property.getEmail(), is("precioustar@kakao.com"));
    }

}