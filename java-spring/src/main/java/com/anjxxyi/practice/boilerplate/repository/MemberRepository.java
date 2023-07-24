package com.anjxxyi.practice.boilerplate.repository;

import com.anjxxyi.practice.boilerplate.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Mapper
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Member> findByEmailAndProvider(String email, String provider);
}
