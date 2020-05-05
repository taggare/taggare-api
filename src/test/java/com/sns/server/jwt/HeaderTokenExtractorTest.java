package com.sns.server.jwt;

import com.sns.server.security.HeaderTokenExtractor;
import com.sns.server.security.filters.JwtAuthenticationFilter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeaderTokenExtractorTest {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private HeaderTokenExtractor extractor = new HeaderTokenExtractor();
    private String header;

    @Before
    public void setUp() {
        this.header = "Bearer asjdkqjwkl.ajsdljsdlkf";
    }

    @Test
    public void JWT_헤더_토큰_추출() {
        assertThat(extractor.extract(this.header), is("asjdkqjwkl.ajsdljsdlkf"));
    }
}
