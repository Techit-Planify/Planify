package com.ll.planify.global.initData;

import com.ll.planify.domain.member.member.dto.MemberRegisterDto;
import com.ll.planify.domain.member.member.entity.Member;
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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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

                // 사용자 정보 가져오기
                Optional<Member> user1Optional = memberService.findByUsername("user1");
                Optional<Member> user2Optional = memberService.findByUsername("user2");

                Member user1 = user1Optional.get();
                Member user2 = user2Optional.get();

                String[] contents = {"장보기", "운동하기", "도서 읽기", "노트북 매장 방문", "영화 보기", "친구 만나기", "프로젝트 준비", "코딩 연습", "여행 계획", "일기 쓰기"};
                String[] hashtags = {"#쇼핑", "#건강", "#취미", "#외출", "#휴식", "#친구", "#프로젝트", "#코딩", "#여행", "#일기"};

                Random random = new Random();

                // 각 유저별로 20개의 할 일 생성
                for (int i = 0; i < 20; i++) {
                    Todo todo1 = new Todo();
                    todo1.setContent(contents[random.nextInt(contents.length)]);
                    todo1.setDeadline(LocalDateTime.now().plusDays(random.nextInt(30) + 1));
                    todo1.setPriority(TodoPriority.values()[random.nextInt(TodoPriority.values().length)]);
                    todo1.setStatus(TodoStatus.values()[random.nextInt(TodoStatus.values().length)]);
                    todo1.setMember(user1);
                    todoService.save(todo1);

                    String hash1 = hashtags[random.nextInt(hashtags.length)] + " " + hashtags[random.nextInt(hashtags.length)];
                    hashtagService.addHashtags(todo1, hash1);

                    Todo todo2 = new Todo();
                    todo2.setContent(contents[random.nextInt(contents.length)]);
                    todo2.setDeadline(LocalDateTime.now().plusDays(random.nextInt(30) + 1));
                    todo2.setPriority(TodoPriority.values()[random.nextInt(TodoPriority.values().length)]);
                    todo2.setStatus(TodoStatus.values()[random.nextInt(TodoStatus.values().length)]);
                    todo2.setMember(user2);
                    todoService.save(todo2);

                    String hash2 = hashtags[random.nextInt(hashtags.length)] + " " + hashtags[random.nextInt(hashtags.length)];
                    hashtagService.addHashtags(todo2, hash2);
                }
            }
        };
    }
}