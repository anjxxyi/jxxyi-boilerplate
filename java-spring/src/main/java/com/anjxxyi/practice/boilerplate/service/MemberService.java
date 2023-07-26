package com.anjxxyi.practice.boilerplate.service;

import com.anjxxyi.practice.boilerplate.model.Member;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberResponseDto;
import com.anjxxyi.practice.boilerplate.repository.MemberRepository;
import com.anjxxyi.practice.boilerplate.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    // 응답 데이터 전송 객체 => Type.유저정보찾기
    public MemberResponseDto getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                // [오류사항] MemberId가 존재하지 않을 경우
                .orElseThrow(() -> new RuntimeException("회원정보가 존재하지 않습니다."))
                ;
    }

    /* @Transactional
     * - 위 어노테이션이 붙은 메서드는 메서드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 취소함
     * - DB와 관련된, 트랜잭션이 필요한 서비스 클래스 혹은 메서드에 사용 (단, id는 롤백되지 않음)
     * */
    @Transactional
    public MemberResponseDto changeMemberNickname(String email, String nickname) {
        // * nickname 변경
        Member member = memberRepository.findByEmail(email)         // 1. 받은 값1(email)으로 사용자 탐색
                // [오류사항] email이 존재하지 않을 경우
                .orElseThrow(() -> new RuntimeException("입력하신 이메일이 존재하지 않습니다."))
        ;
        member.setNickname(nickname);                               // 2. email이 존재하면 받은 값2(nickname) 변경
        return MemberResponseDto.of(memberRepository.save(member)); // 3. 저장
    }

    @Transactional
    public MemberResponseDto changeMemberPassword‎(String email, String exPassword, String newPassword) {
        // * password 변경
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                // [오류사항] MemberId가 존재하지 않을 경우
                .orElseThrow(() -> new RuntimeException("회원정보가 존재하지 않습니다."))
        ;
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            // [오류사항] exPassword(기존 비밀번호)가 일치하지 않을 경우
            throw new RuntimeException("기존 비밀번호가 일치하지 않습니다.");
        }
        member.setPassword(passwordEncoder.encode(newPassword));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    public Member getMemberByEmail(String email) {
        // * email 탐색 ~> 계정찾기
        return memberRepository.findByEmail(email)
                // [오류사항] email이 존재하지 않을 경우
                .orElseThrow(() -> new IllegalArgumentException("입력하신 이메일이 존재하지 않습니다."));
    }
}
