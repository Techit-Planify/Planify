package com.ll.planify.global.initData;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("!prod")
@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Order(2)
    public ApplicationRunner initNotProd() {
        return args -> {
            if (!memberService.findByUsername("admin").isPresent()) {
                MemberRegisterDto adminDto = new MemberRegisterDto("admin", "admin", passwordEncoder.encode("admin"), "admin", null);
                MemberRegisterDto user1Dto = new MemberRegisterDto("user1@example.com", "user1", passwordEncoder.encode("1234"), "user1", null);
                MemberRegisterDto user2Dto = new MemberRegisterDto("user2@example.com", "user2", passwordEncoder.encode("1234"), "user2", null);
                memberService.register(adminDto);
                memberService.register(user1Dto);
                memberService.register(user2Dto);
            }
        };
    }
}
