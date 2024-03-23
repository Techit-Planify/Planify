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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String content;

    private LocalDate deadline; // 마감일 설정

    @Enumerated(EnumType.STRING)
    private TodoPriority priority; // 우선순위[높음, 중간, 낮음]

    @Enumerated(EnumType.STRING)
    private TodoStatus status; // 진행여부[PROGRESS, DONE]

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Hashtag> hashtags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}