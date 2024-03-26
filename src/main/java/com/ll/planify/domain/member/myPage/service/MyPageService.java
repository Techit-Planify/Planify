package com.ll.planify.domain.member.myPage.service;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.member.member.repository.MemberRepository;
import com.ll.planify.domain.member.myPage.dto.MemberInfoDto;
import com.ll.planify.domain.member.myPage.dto.UpdateMemberNicknameDto;
import com.ll.planify.domain.member.myPage.dto.UpdateMemberPasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberInfoDto getMemberInfo(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = optionalMember.get();
        return new MemberInfoDto(member.getEmail(), member.getUsername(), member.getNickname());
    }

    @Transactional
    public void updateNickname(UpdateMemberNicknameDto updateMemberNicknameDto, Long id) {
        Member member = memberRepository.findById(id).get();
        member.updateMemberNickname(updateMemberNicknameDto);
    }

    public boolean checkCurrentPassword(UpdateMemberPasswordDto updateMemberPasswordDto, Long id) {
        Member member = memberRepository.findById(id).get();
        return passwordEncoder.matches(updateMemberPasswordDto.getPassword(), member.getPassword());
    }

    public boolean checkNewPassword(UpdateMemberPasswordDto updateMemberPasswordDto) {
        return updateMemberPasswordDto.getNewPassword().equals(updateMemberPasswordDto.getNewPasswordConfirm());
    }

    @Transactional
    public void updatePassword(UpdateMemberPasswordDto updateMemberPasswordDto, Long id) {
        Member member = memberRepository.findById(id).get();
        member.updateMemberPassword(passwordEncoder.encode(updateMemberPasswordDto.getNewPassword()));
    }

    public boolean checkNickname(UpdateMemberNicknameDto updateMemberNicknameDto, Long id) {
        Optional<Member> optionalMember = memberRepository.findByNickname(updateMemberNicknameDto.getNickname());
        return optionalMember.isPresent() && !optionalMember.get().getId().equals(id);
    }
}
