package com.ll.planify.domain.member.myPage.controller;

import com.ll.planify.domain.member.myPage.dto.MemberInfoDto;
import com.ll.planify.domain.member.myPage.service.MyPageService;
import com.ll.planify.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/my-page")
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("")
    public String showInfo(@AuthenticationPrincipal CustomUserDetails user, Model model){
        MemberInfoDto memberInfoDto = myPageService.getMemberInfo(user.getId());
        model.addAttribute("memberInfoDto", memberInfoDto);
        return "domain/member/myPage/memberInfo";
    }

    

}
