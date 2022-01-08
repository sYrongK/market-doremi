package com.doremi.marketdoremi.web;

import com.doremi.marketdoremi.common.config.security.CustomUserDetailsService;
import com.doremi.marketdoremi.common.config.security.MemberDetail;
import com.doremi.marketdoremi.service.member.MemberService;
import com.doremi.marketdoremi.web.dto.MemberDto;
import com.doremi.marketdoremi.web.dto.MemberRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/member")
    public String signup(@RequestBody MemberDto requestDto) {
        String memberId = memberService.joinUser(requestDto);
        log.info("user created::" + requestDto.toString());
        return "redirect:/" + memberId;
    }

    @PostMapping("/api/v1/member")
    public ResponseEntity<String> signup(@RequestBody MemberRequest memberRequest) {
        String memberId = memberService.joinMember(memberRequest);
        return ResponseEntity.ok(memberId);
    }

    @GetMapping("/login")
    public ResponseEntity<MemberDetail> login(@RequestBody MemberDto memberDto) {
        try {
            MemberDetail member = memberService.login(memberDto);
            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}

