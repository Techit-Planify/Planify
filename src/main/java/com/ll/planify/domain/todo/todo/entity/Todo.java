package com.ll.planify.domain.todo.todo.entity;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime deadline; // 마감일 설정

    @Enumerated(EnumType.STRING)
    private TodoPriority priority; // 우선순위 [없음, 낮음, 중간, 높음]

    @Enumerated(EnumType.STRING)
    private TodoStatus status; // 진행여부[PROGRESS, DONE]

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hashtag> hashtags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}