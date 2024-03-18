package com.ll.planify.domain.member.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberRegisterDto {
    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    private String imageUrl;
}
