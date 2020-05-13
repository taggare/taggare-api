package com.sns.server.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginDto {

    @JsonProperty("provider")
    private SocialProviders provider;

    @JsonProperty("token")
    private String token;

}
