package com.sns.server.security;

import com.sns.server.exceptions.jwt.InvalidTokenException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HeaderTokenExtractor {

    // 스페이스 공백을 넣고 prefix 설정.
    public static final String HEADER_PREFIX = "Bearer ";

    public String extract(String header) {

        if (StringUtils.isEmpty(header) || header.length() < HEADER_PREFIX.length()) {
            throw new InvalidTokenException();
        }

        return header.substring(HEADER_PREFIX.length(),  header.length());
    }
}
