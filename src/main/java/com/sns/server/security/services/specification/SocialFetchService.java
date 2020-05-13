package com.sns.server.security.services.specification;

import com.sns.server.account.SocialLoginDto;
import com.sns.server.security.social.SocialUserProperty;

public interface SocialFetchService {
    SocialUserProperty getSocialUserInfo(SocialLoginDto socialLoginDto);
}
