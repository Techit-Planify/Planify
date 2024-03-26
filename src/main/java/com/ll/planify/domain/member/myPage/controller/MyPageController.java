package com.ll.planify.domain.member.myPage.controller;

import com.ll.planify.domain.member.myPage.dto.MemberInfoDto;
import com.ll.planify.domain.member.myPage.dto.UpdateMemberNicknameDto;
import com.ll.planify.domain.member.myPage.dto.UpdateMemberPasswordDto;
import com.ll.planify.domain.member.myPage.service.MyPageService;
import com.ll.planify.global.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/my-page")
@Slf4j
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("")
    public String showInfo(@AuthenticationPrincipal CustomUserDetails user, Model model){
        MemberInfoDto memberInfoDto = myPageService.getMemberInfo(user.getId());
        model.addAttribute("memberInfoDto", memberInfoDto);
        boolean isSocialLogin = myPageService.IsSocialLogin(user.getId());
        model.addAttribute(("isSocialLogin"), isSocialLogin);
        return "domain/member/myPage/memberInfo";
    }

    @GetMapping("/update")
    public String showUpdateNickname(@AuthenticationPrincipal CustomUserDetails user, Model model){
        MemberInfoDto memberInfoDto = myPageService.getMemberInfo(user.getId());
        model.addAttribute("memberInfoDto", memberInfoDto);
        model.addAttribute("updateMemberNicknameDto", new UpdateMemberNicknameDto(memberInfoDto.getNickname()));
        return "domain/member/myPage/updateNickname";
    }

    @PutMapping("/update")
    public String UpdateNickname(@AuthenticationPrincipal CustomUserDetails user, @Valid UpdateMemberNicknameDto updateMemberNicknameDto,  BindingResult bindingResult, Model model){
        MemberInfoDto memberInfoDto = myPageService.getMemberInfo(user.getId());
        if(bindingResult.hasErrors()){
            model.addAttribute("memberInfoDto", memberInfoDto);
            return "domain/member/myPage/updateNickname";
        }
        if(myPageService.checkNickname(updateMemberNicknameDto, user.getId())){
            model.addAttribute("memberInfoDto", memberInfoDto);
            bindingResult.rejectValue("nickname", "existNickname", "중복되는 닉네임이 존재합니다.");
            return "domain/member/myPage/updateNickname";
        }
        myPageService.updateNickname(updateMemberNicknameDto, user.getId());
        return "redirect:/member/my-page";
    }

    @GetMapping("/update-password")
    public String showUpdatePassword(Model model){
        model.addAttribute("updateMemberPasswordDto", new UpdateMemberPasswordDto());
        return "domain/member/myPage/updatePassword";
    }

    @PutMapping("/update-password")
    public String UpdatePassword(@AuthenticationPrincipal CustomUserDetails user, @Valid UpdateMemberPasswordDto updateMemberPasswordDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "domain/member/myPage/updatePassword";
        }
        if (!myPageService.checkCurrentPassword(updateMemberPasswordDto, user.getId())){
            bindingResult.rejectValue("password", "mismatchCurrentPassword", "현재 비밀번호가 일치하지 않습니다.");
            return "domain/member/myPage/updatePassword";
        }
        if(!myPageService.checkNewPassword(updateMemberPasswordDto)){
            bindingResult.rejectValue("newPasswordConfirm", "mismatchNewPassword", "새로운 비밀번호가 일치하지 않습니다.");
            return "domain/member/myPage/updatePassword";
        }

        myPageService.updatePassword(updateMemberPasswordDto, user.getId());

        return "redirect:/member/my-page";
    }

    @DeleteMapping("/delete-account")
    private String deleteMember(@AuthenticationPrincipal CustomUserDetails user, HttpServletResponse response, HttpServletRequest request){
        myPageService.deleteMember(user.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/?logout";
    }

}
