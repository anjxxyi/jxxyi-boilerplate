package com.anjxxyi.practice.boilerplate.util;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

//@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class SecurityUtil {
    private SecurityUtil() {}

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 세션 사용자 정보 찾기 -> SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("[!] 보안 컨택스트(Security context)에 인증정보(Authentication)가 없습니다.");
            // Context -> 호출, 응답 간의 환경 정보
        }
        String name = authentication.getName();
        return Long.parseLong(name);
    }
}
