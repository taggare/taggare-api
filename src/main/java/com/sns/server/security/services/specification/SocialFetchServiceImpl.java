package com.sns.server.security.services.specification;

import com.sns.server.account.SocialLoginDto;
import com.sns.server.account.SocialProviders;
import com.sns.server.security.social.SocialUserProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialFetchServiceImpl implements SocialFetchService {

    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public SocialUserProperty getSocialUserInfo(SocialLoginDto socialLoginDto) {
        SocialProviders provider = socialLoginDto.getProvider();
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>("parameter", generateHeader(socialLoginDto.getToken()));
        return restTemplate.exchange(provider.getUserInfoEndPoint(), HttpMethod.GET, entity, provider.getPropertyMetaClass()).getBody();
    }

    private HttpHeaders generateHeader(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", generateHeaderContent(token));
        return httpHeaders;
    }

    private String generateHeaderContent(String token) {
        StringBuilder sb = new StringBuilder();

        sb.append(HEADER_PREFIX);
        sb.append(token);

        return sb.toString();
    }
}
