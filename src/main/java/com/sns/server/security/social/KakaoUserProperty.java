package com.sns.server.security.social;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class KakaoUserProperty implements SocialUserProperty {

    @JsonProperty("kakao_account")
    private Map<String, Object> kakaoAccount;

    @JsonProperty("id")
    private Long userUniquedId;

    @JsonProperty("properties")
    private Map<String, String> userProperties;

    @Override
    public String getUserId() {
        return userUniquedId.toString();
    }

    @Override
    public String getUserNickname() {
        return userProperties.get("nickname");
    }

    @Override
    public String getProfileHref() {
        return userProperties.get("profile_image");
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }
}
