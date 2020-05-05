package com.sns.server.security;

import com.sns.server.security.filters.FormLoginFilter;
import com.sns.server.security.filters.JwtAuthenticationFilter;
import com.sns.server.security.handlers.FormLoginAuthenticationSuccessHandler;
import com.sns.server.security.handlers.JwtAuthenticationFailureHandler;
import com.sns.server.security.providers.FormLoginAuthenticationProvider;
import com.sns.server.security.providers.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    private final FormLoginAuthenticationProvider formLoginAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final HeaderTokenExtractor headerTokenExtractor;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // swagger 리소스 시큐리티 필터 제거
        web.ignoring().antMatchers(
                "/", "/h2/**",
                "/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(accountContextService).passwordEncoder(passwordEncoder());
        auth
                .authenticationProvider(this.formLoginAuthenticationProvider)
                .authenticationProvider(this.jwtAuthenticationProvider);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter("/formLogin", formLoginAuthenticationSuccessHandler, null);
        formLoginFilter.setAuthenticationManager(super.authenticationManagerBean());
        return formLoginFilter;
    }

    protected JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        FilterSkipMatcher filterSkipMatcher = new FilterSkipMatcher(Arrays.asList("/formLogin"), "/users/hello");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(filterSkipMatcher, jwtAuthenticationFailureHandler, headerTokenExtractor);
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }
}
