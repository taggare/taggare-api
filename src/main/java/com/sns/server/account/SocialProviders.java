package com.sns.server.account;

import com.fasterxml.jackson.annotation.JsonValue;
import com.sns.server.security.social.KakaoUserProperty;
import com.sns.server.security.social.SocialUserProperty;
import lombok.Getter;

@Getter
public enum SocialProviders {

    KAKAO("https://kapi.kakao.com/v2/user/me", KakaoUserProperty.class);;

    private String userInfoEndPoint;
    private Class<? extends SocialUserProperty> propertyMetaClass;

    SocialProviders(String userInfoEndPoint, Class<? extends SocialUserProperty> propertyMetaClass) {
        this.userInfoEndPoint = userInfoEndPoint;
        this.propertyMetaClass = propertyMetaClass;
    }

    @JsonValue
    public String getProviderName() {
        return this.name();
    }
}
