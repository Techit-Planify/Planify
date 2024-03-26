package com.ll.planify.domain.member.myPage.service;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.member.member.repository.MemberRepository;
import com.ll.planify.domain.member.myPage.dto.UpdateMemberNicknameDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MyPageServiceTest {
    @Autowired
    private MyPageService myPageService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void updateNickname() {
        // given: 테스트에 필요한 데이터 준비
        String newNickname = "newNickname";
        Long memberId = 1L; // 테스트 대상 회원의 ID. 테스트 환경에 맞게 적절한 값으로 변경해야 합니다.

        // when: 닉네임 업데이트 실행
        UpdateMemberNicknameDto dto = new UpdateMemberNicknameDto(newNickname);
        myPageService.updateNickname(dto, memberId);

        // then: 업데이트된 닉네임 검증
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        assertEquals(newNickname, member.getNickname());
    }
}