package com.anjxxyi.practice.boilerplate.jpa;

import com.anjxxyi.practice.boilerplate.model.Member;
import com.anjxxyi.practice.boilerplate.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JpaTest {
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("jpa 사용 가능 여부 테스트")
    @Test
    void jpaTest1() {
        Member member = memberRepository.findById(2L).get();
        assertThat(member.getEmail()).isEqualTo("test20@test.com");

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList.size()).isEqualTo(3);
    }
}
