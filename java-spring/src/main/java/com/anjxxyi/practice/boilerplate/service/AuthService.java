package com.anjxxyi.practice.boilerplate.service;

import com.anjxxyi.practice.boilerplate.config.jwt.JwtTokenProvider;
import com.anjxxyi.practice.boilerplate.model.Member;
import com.anjxxyi.practice.boilerplate.model.dtos.JwtTokenDto;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberRequestDto;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberResponseDto;
import com.anjxxyi.practice.boilerplate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponseDto signup(MemberRequestDto requestDto) {
        // └▶ (1) 받는 값 : {email, password, nickname}

        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        // └▶ (2) email이 중복인 경우 예외처리

        Member member = requestDto.toMember(passwordEncoder);
        // └▶ (3) Member 객체 생성

        return MemberResponseDto.of(memberRepository.save(member));
        // => 리턴 값 {email, nickname}
    }


    public JwtTokenDto signin(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        // └▶ (1) UsernamePasswordAuthenticationToken 인스턴스 생성 <- email, password 조합

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        // └▶ (2) AuthenticationManager에게 인스턴스 전달 => 검증단계
        //    (3) Authentication 인스턴스 리턴 <- AuthenticationManager 인증성공

        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateTokenDto(authentication);
        // └▶ (4) SecurityContextHolder.getContext().setAuthentication(...) 저장 <- Authentication 전달

        Optional<Member> optMember = memberRepository.findByEmail(requestDto.getEmail());
        // └▶ (5) email로 유저정보를 가져와 optMember 객체 생성
        //        ~> Optional : Null일 수도 있는 객체를 감싸는 일종의 Wrapper 클래스 => 반복적인 null 체크를 줄일 수 있음.

        Member member = optMember.isPresent() ? optMember.get() : null;
        // └▶ (6) isPresent()으로 Optional에 객체 유무 체크 -> 있다면 get()으로 member 객체 반환

        if (Objects.nonNull(member)) {
            member.setAccessToken(jwtTokenDto.getAccessToken());
            member.setAccessTokenExpireIn(jwtTokenDto.getTokenExpiresIn());
            memberRepository.save(member);
        }
        // └▶ (7) Null 값 체크 : {accessToken, tokenExpiresIn} 값 추가하여 저장

        return jwtTokenDto;
        // => 리턴 값 {grantType, accessToken, tokenExpiresIn}
    }

}