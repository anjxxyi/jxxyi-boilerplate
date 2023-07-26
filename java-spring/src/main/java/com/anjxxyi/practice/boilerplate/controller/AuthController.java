package com.anjxxyi.practice.boilerplate.controller;

import com.anjxxyi.practice.boilerplate.service.AuthService;
import com.anjxxyi.practice.boilerplate.model.dtos.JwtTokenDto;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberRequestDto;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController             // Json 형태로 객체 데이터를 반환 => REST API를 개발할 때 주로 사용
@RequestMapping("/auth")    // 요청이 왔을 때 어떤 컨트롤러가 호출이 되어야 하는지 알려주는 지표
@RequiredArgsConstructor    // final 혹은 @NotNull이 붙은 필드 생성자 자동생성
// :: 인증이 안된 상태여도 접속 가능한 컨트롤러 ~> WebSecurityConfig 클래스에서 선언 > "/auth/**"
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
        // └▶ 회원가입 성공 -> <MemberResponseDto> DTO 출력 {email, nickname}
    }

    @PostMapping("/signin") // 로그인
    public ResponseEntity<JwtTokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signin(requestDto));
        // └▶ 로그인 성공 -> <JwtTokenDto> DTO 출력 {grantType(토큰 타입), accessToken(접근 토큰), tokenExpiresIn(토큰 유효기간)}
    }
}
