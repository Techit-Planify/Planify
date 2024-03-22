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
        } else if (!memberRegisterDto.getPassword().equals(memberRegisterDto.getPasswordConfirm())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .email(memberRegisterDto.getEmail())
                .username(memberRegisterDto.getUsername())
                .password(passwordEncoder.encode(memberRegisterDto.getPassword()))
                .nickname(memberRegisterDto.getNickname())
                .providerId(null)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public Member whenSocialLogin(String providerTypeCode, String providerId, String nickname, String name, String email) {
        Optional<Member> optionalMember = findByProviderId(providerId);
        if (optionalMember.isPresent()) {
            return optionalMember.get();
        }

        String uniqueNickname = nicknameIsExist(nickname) ? generateUniqueNickname(nickname) : nickname;

        // 중복 코드 제거를 위해 Member 객체 생성 로직을 단일화
        Member member = Member.builder()
                .email(email)
                .username(name) // formatted 메소드 대신 "+" 연산자 사용
                .password(null)
                .nickname(uniqueNickname)
                .providerId(providerId)
                .build();

        memberRepository.save(member);
        return member;
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

    public boolean confirmPassword(String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

    public Optional<Member> findByProviderId(String providerId) {
        return memberRepository.findByProviderId(providerId);
    }

    public String generateUniqueNickname(String nickname){
        int suffix = 1;
        String uniqueNickname = nickname + suffix;

        while (memberRepository.existsByNickname(uniqueNickname)){
            suffix++;
            uniqueNickname = nickname + suffix;
        }

        return uniqueNickname;
    }
}