package com.anjxxyi.practice.boilerplate.jwt;

import com.anjxxyi.practice.boilerplate.config.jwt.JwtTokenProvider;
import com.anjxxyi.practice.boilerplate.model.dtos.JwtTokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootTest
public class JwtTest {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("JWT 토큰 생성 테스트")
    @Test
    void jwtGenerateTest1() {
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDto("1");

        System.out.println("grant type: " + jwtTokenDto.getGrantType());
        System.out.println("access token: " + jwtTokenDto.getAccessToken());

        // unixtimestamp을 읽기 쉬운 시간으로 변경
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(jwtTokenDto.getTokenExpiresIn()),
                        TimeZone.getDefault().toZoneId());
        System.out.println("access token expire time: " + localDateTime);
    }
}
