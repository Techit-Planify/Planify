package com.ll.planify.domain.member.member.entity;

import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    @Column(unique = true)
    private String nickname;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Todo> todoList;

}