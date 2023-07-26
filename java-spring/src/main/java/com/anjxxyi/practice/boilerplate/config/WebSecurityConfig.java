package com.anjxxyi.practice.boilerplate.config;

import com.anjxxyi.practice.boilerplate.config.jwt.JwtAccessDeniedHandler;
import com.anjxxyi.practice.boilerplate.config.jwt.JwtTokenProvider;
import com.anjxxyi.practice.boilerplate.config.oauth.JwtAuthenticationEntryPoint;
import com.anjxxyi.practice.boilerplate.config.oauth.OAuth2CustomAuthenticationSuccessHandler;
import com.anjxxyi.practice.boilerplate.config.oauth.OAuth2CustomUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Component
@Configuration
@EnableWebSecurity // -> SpringSecurityFilterChain이 자동으로 포함
// :: Security 환경설정 (권한설정, 필터 추가 등등)
public class WebSecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2CustomUserService oAuth2CustomUserService;

    @Bean // Spring Security를 적용하지 않을 리소스 설정
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean // Spring Security의 세부 설정
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.httpBasic((basic)-> basic.disable())
            .csrf((csrf)-> csrf.disable())
            .formLogin((formlogin)-> formlogin.disable())
            .sessionManagement((session)-> session // : 스프링시큐리티 세션정책
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).exceptionHandling((exception)-> exception // : 예외처리 기능
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            ).authorizeHttpRequests((authorize) -> authorize // : 인증&인가 설정 (기본상태: 미적용)
                .requestMatchers(mvcMatcherBuilder.pattern("/auth/**")).permitAll()
                .anyRequest().authenticated()
                // └▶ "/auth/**" 제외한 모든 경로에서 어떠한 요청이든지 인증 필요
            ).oauth2Login((authlogin) -> authlogin // : 인증 로그인 설정
                .successHandler(customAuth2SuccessHandler())
                .userInfoEndpoint((userinfo)-> userinfo.userService(oAuth2CustomUserService))
                // └▶ OAuth2 로그인 성공 후 사용자 정보를 가져올 때 설정 : 리소스 서버(Google, 네이버, 카카오 등)에서 추가 진행하고자 하는 기능
            );
        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

    @Bean // SuccessHandler bean register
    public OAuth2CustomAuthenticationSuccessHandler customAuth2SuccessHandler() {
        return new OAuth2CustomAuthenticationSuccessHandler(oAuth2CustomUserService);
    }

    @Bean // PasswordEncoder bean register
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}