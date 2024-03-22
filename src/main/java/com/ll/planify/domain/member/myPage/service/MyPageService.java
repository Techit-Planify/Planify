package com.ll.planify.domain.member.myPage.service;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.member.member.repository.MemberRepository;
import com.ll.planify.domain.member.myPage.dto.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;

    public MemberInfoDto getMemberInfo(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        Member member = optionalMember.get();
        return new MemberInfoDto(member.getEmail(), member.getUsername(), member.getNickname());
    }
}
