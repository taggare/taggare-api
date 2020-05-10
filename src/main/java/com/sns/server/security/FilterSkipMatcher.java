package com.sns.server.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher processingMatcher;

    /**
     * 로그인하기전과 같은 특정 상황(토큰이 처음에 없는 경우)에는 필터대상의 요청을 제외하고,
     * 나머지는 헤더에 토큰을 실어서 필터 프로세스를 진행한다.
     *
     * @param pathToSkip     제외할 필터 경로
     * @param processingPath 요청을 가로챌 필터 경로 설정
     */
    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        this.orRequestMatcher =
                new OrRequestMatcher(pathToSkip.stream().map(p -> new AntPathRequestMatcher(p)).collect(Collectors.toList()));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}
