package com.ll.planify.domain.member.myPage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoDto {
    private String email;
    private String username;
    private String nickname;
}
