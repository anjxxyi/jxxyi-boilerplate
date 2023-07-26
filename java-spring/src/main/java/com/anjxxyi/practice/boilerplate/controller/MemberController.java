package com.anjxxyi.practice.boilerplate.controller;

import com.anjxxyi.practice.boilerplate.model.dtos.ChangePasswordRequestDto;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberRequestDto;
import com.anjxxyi.practice.boilerplate.model.dtos.MemberResponseDto;
import com.anjxxyi.practice.boilerplate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me") // GET -> 계정정보 확인
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println("[성공] 닉네임 : " + myInfoBySecurity.getNickname());
        return ResponseEntity.ok(myInfoBySecurity);
    }

    @PostMapping("/nickname") // POST -> (email, nickname) : 닉네임 수정
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(
                memberService.changeMemberNickname(request.getEmail(), request.getNickname())
        );
    }

    @PostMapping("/password") // POST -> (email, exPassword, newPassword) : 비밀번호 수정
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(
                memberService.changeMemberPassword‎(request.getEmail(), request.getExPassword(), request.getNewPassword())
        );
    }


}
