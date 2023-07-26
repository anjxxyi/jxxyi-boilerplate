package com.anjxxyi.practice.boilerplate.config.oauth;

import com.anjxxyi.practice.boilerplate.model.Member;
import com.anjxxyi.practice.boilerplate.model.dtos.JwtTokenDto;
import com.anjxxyi.practice.boilerplate.model.enums.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfile {
    private String email;
    private String provider;
    private String nickname;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .provider(provider)
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public Member toMember(JwtTokenDto jwtTokenDto) {
        return Member.builder()
                .email(email)
                .provider(provider)
                .nickname(nickname)
                .accessToken(jwtTokenDto.getAccessToken())
                .accessTokenExpireIn(jwtTokenDto.getTokenExpiresIn())
                .authority(Authority.ROLE_USER)
                .build();
    }
}