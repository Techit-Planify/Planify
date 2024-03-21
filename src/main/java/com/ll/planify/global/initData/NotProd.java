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
            if (memberService.findByUsername("admin").isEmpty()) {
                MemberRegisterDto adminDto = new MemberRegisterDto("admin", "admin", "admin", "admin", "admin");
                MemberRegisterDto user1Dto = new MemberRegisterDto("user1@example.com", "user1", "1234", "1234", "user1");
                MemberRegisterDto user2Dto = new MemberRegisterDto("user2@example.com", "user2", "1234", "1234", "user2");
                memberService.register(adminDto);
                memberService.register(user1Dto);
                memberService.register(user2Dto);
            }
        };
    }
}
