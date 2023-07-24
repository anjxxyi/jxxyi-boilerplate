package com.anjxxyi.practice.bolierplate.repository;

import com.anjxxyi.practice.bolierplate.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Optional;

@Mapper
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> selectMember(HashMap<String, Object> map);
}
