package com.ll.planify.domain.member.myPage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberPasswordDto {
    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 8, max = 16, message = "비밀번호는 8자리 이상 16자리 이하로 설정해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$", message = "비밀번호는 영문과 숫자를 포함해야 합니다.")
    private String newPassword;
    @NotBlank
    private String newPasswordConfirm;
}
