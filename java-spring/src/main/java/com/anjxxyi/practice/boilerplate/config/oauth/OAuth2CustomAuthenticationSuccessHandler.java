package com.anjxxyi.practice.boilerplate.config.oauth;

import com.anjxxyi.practice.boilerplate.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RequiredArgsConstructor
@Component
//public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
public class OAuth2CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    public static final String REDIRECT_URI = "/articles";
    public static final String REDIRECT_URI = "http://localhost:3000/oauth2login/callback";
//    public static final String REDIRECT_URI = "http://localhost:3000";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

//    private final JwtTokenProvider jwtTokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
//    private final MemberService memberService;
    private final OAuth2CustomUserService oAuth2CustomUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        System.out.println("oAuth2User: " + oAuth2User);
//        Member member = memberService.findByEmail((String) oAuth2User.getAttributes().get("email"));
        String loginEmail = (String) oAuth2User.getAttributes().get("email");
        Member member = oAuth2CustomUserService.getMemberByEmail(loginEmail);
        response.sendRedirect(UriComponentsBuilder.fromUriString(REDIRECT_URI)
                .queryParam("accessToken", member.getAccessToken())
                .queryParam("expirationTimeIn", member.getAccessTokenExpireIn())
                .queryParam("refreshToken", "refreshToken")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }
}