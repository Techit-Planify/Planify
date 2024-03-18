package com.ll.planify.domain.member.member.controller;

import com.ll.planify.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
        if (user == null) {
            return ResponseEntity.ok("로그인이 되어있지 않습니다.");
        } else {
            // 실제 환경에서는 사용자 정보를 더 상세하게 반환할 수 있습니다.
            return ResponseEntity.ok(user.getUsername() + "님, 로그인됨");
        }
    }
}