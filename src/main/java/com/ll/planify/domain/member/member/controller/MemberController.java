package com.ll.planify.domain.member.member.controller;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String showSingUp(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "domain/member/member/sign-up";
    }

    @PostMapping("/sign-up")
    public String register(@Valid MemberRegisterDto memberRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/member/member/sign-up";
        }
        if (memberService.emailIsExist(memberRegisterDto.getEmail())) {
            bindingResult.rejectValue("email", "existemail", "이미 등록된 이메일입니다.");
            return "domain/member/member/sign-up";
        }
        if (memberService.nicknameIsExist(memberRegisterDto.getNickname())) {
            bindingResult.rejectValue("nickname", "existNickname", "이미 등록된 닉네임입니다.");
            return "domain/member/member/sign-up";
        }
        if (!memberService.confirmPassword(memberRegisterDto.getPassword(), memberRegisterDto.getPasswordConfirm())) {
            bindingResult.rejectValue("password", "mismatchPassword", "비밀번호, 비밀번호 확인이 일치하지 않습니다.");
            return "domain/member/member/sign-up";
        }
        memberService.register(memberRegisterDto);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "domain/member/member/login";
    }
}