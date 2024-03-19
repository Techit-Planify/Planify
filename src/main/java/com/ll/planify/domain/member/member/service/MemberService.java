package com.ll.planify.domain.member.member.service;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(MemberRegisterDto memberRegisterDto){
        if(emailIsExist(memberRegisterDto.getEmail())){
            throw new RuntimeException("이미 등록된 이메일입니다.");
        } else if (nicknameIsExist(memberRegisterDto.getNickname())) {
            throw new RuntimeException("이미 등록된 닉네임입니다.");
        } else if (memberRegisterDto.getPassword().equals(memberRegisterDto.getPasswordConfirm())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .email(memberRegisterDto.getEmail())
                .username(memberRegisterDto.getUsername())
                .password(passwordEncoder.encode(memberRegisterDto.getPassword()))
                .nickname(memberRegisterDto.getNickname())
                .build();

        memberRepository.save(member);
    }

    public boolean emailIsExist(String email){
        return memberRepository.existsByEmail(email);
    }

    public boolean nicknameIsExist(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }


}
