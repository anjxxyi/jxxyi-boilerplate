package com.anjxxyi.practice.boilerplate.model.dtos;

import com.anjxxyi.practice.boilerplate.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 필드의 생성자 자동생성
@NoArgsConstructor  // final이나 @NotNull이 붙은 필드의 생성자 자동생성
@Builder            // 파라미터를 활용하여 빌더 패턴을 자동생성
public class MemberResponseDto {
    private String email;
    private String nickname;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
