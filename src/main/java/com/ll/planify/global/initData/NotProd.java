package com.ll.planify.global.initData;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.service.MemberService;
<<<<<<< HEAD
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.service.TodoService;
=======
>>>>>>> 48efb830095015bc6edb1eeed80c4ab9565f2736
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

<<<<<<< HEAD
import java.time.LocalDate;

=======
>>>>>>> 48efb830095015bc6edb1eeed80c4ab9565f2736
@Profile("!prod")
@Configuration
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
<<<<<<< HEAD
    private final TodoService todoService;
=======
>>>>>>> 48efb830095015bc6edb1eeed80c4ab9565f2736
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Order(2)
    public ApplicationRunner initNotProd() {
        return args -> {
<<<<<<< HEAD
            if (!memberService.findByUsername("admin").isPresent()) {
                MemberRegisterDto adminDto = new MemberRegisterDto("admin", "admin", passwordEncoder.encode("admin"), "admin", null);
                MemberRegisterDto user1Dto = new MemberRegisterDto("user1@example.com", "user1", passwordEncoder.encode("1234"), "user1", null);
                MemberRegisterDto user2Dto = new MemberRegisterDto("user2@example.com", "user2", passwordEncoder.encode("1234"), "user2", null);
                memberService.register(adminDto);
                memberService.register(user1Dto);
                memberService.register(user2Dto);

                Todo todo1 = Todo.addTodo("장보기", LocalDate.now().plusDays(3));
                Todo todo2 = Todo.addTodo("병원 예약", LocalDate.now().plusDays(5));
                Todo todo3 = Todo.addTodo("여행 계획", LocalDate.now().plusDays(10));

                todoService.save(todo1);
                todoService.save(todo2);
                todoService.save(todo3);
=======
            if (memberService.findByUsername("admin").isEmpty()) {
                MemberRegisterDto adminDto = new MemberRegisterDto("admin", "admin", "admin", "admin", "admin");
                MemberRegisterDto user1Dto = new MemberRegisterDto("user1@example.com", "user1", "1234", "1234", "user1");
                MemberRegisterDto user2Dto = new MemberRegisterDto("user2@example.com", "user2", "1234", "1234", "user2");
                memberService.register(adminDto);
                memberService.register(user1Dto);
                memberService.register(user2Dto);
>>>>>>> 48efb830095015bc6edb1eeed80c4ab9565f2736
            }
        };
    }
}
