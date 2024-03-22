package com.ll.planify.global.initData;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.service.MemberService;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoPriority;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import com.ll.planify.domain.todo.todo.service.HashtagService;
import com.ll.planify.domain.todo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Profile("!prod")
@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final TodoService todoService;
    private final PasswordEncoder passwordEncoder;
    private final HashtagService hashtagService;

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

                Todo t1 = new Todo();
                t1.setContent("장보기~");
                t1.setDeadline(LocalDate.now().plusDays(2));
                t1.setPriority(TodoPriority.낮음);
                t1.setStatus(TodoStatus.진행중);
                todoService.save(t1);

                Todo t2 = new Todo();
                t2.setContent("병원 예약");
                t2.setDeadline(LocalDate.now().plusDays(7));
                t2.setPriority(TodoPriority.중간);
                t2.setStatus(TodoStatus.완료);
                todoService.save(t2);

                Todo t3 = new Todo();
                t3.setContent("레포트 제출");
                t3.setDeadline(LocalDate.now().plusDays(10));
                t3.setPriority(TodoPriority.높음);
                t3.setStatus(TodoStatus.진행중);
                todoService.save(t3);

                String hash1 = "#쇼핑 #식품 #외출";
                hashtagService.addHashtags(t1, hash1);

                String hash2 = "#건강 #외출";
                hashtagService.addHashtags(t2, hash2);
            }
        };
    }
}
