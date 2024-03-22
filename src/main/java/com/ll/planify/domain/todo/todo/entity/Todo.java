package com.ll.planify.domain.todo.todo.entity;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String content;

    private LocalDate deadline; // 마감일 설정

    @Enumerated(EnumType.STRING)
    private TodoPriority priority; // 우선순위 [HIGH, MEDIUM, LOW]

    @Enumerated(EnumType.STRING)
    private TodoStatus status; // 완료 상태 [PROGRESS, DONE]

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hashtag> hashtags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}