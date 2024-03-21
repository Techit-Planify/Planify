package com.ll.planify.domain.member.member.controller;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@PreAuthorize("isAnonymous()")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/sign-up")
    public String showSingUp(Model model){
        return "domain/member/member/sign-up";
    }

    @PostMapping("/sign-up")
    public String register(@Valid MemberRegisterDto memberRegisterDto){
        memberService.register(memberRegisterDto);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "domain/member/member/login";
    }
}