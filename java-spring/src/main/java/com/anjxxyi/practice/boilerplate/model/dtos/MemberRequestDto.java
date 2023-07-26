package com.anjxxyi.practice.boilerplate.model.dtos;

import com.anjxxyi.practice.boilerplate.model.Member;
import com.anjxxyi.practice.boilerplate.model.enums.Authority;

import lombok.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 필드의 생성자 자동생성
@NoArgsConstructor  // final이나 @NotNull이 붙은 필드의 생성자 자동생성
@Builder            // 파라미터를 활용하여 빌더 패턴을 자동생성
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}
