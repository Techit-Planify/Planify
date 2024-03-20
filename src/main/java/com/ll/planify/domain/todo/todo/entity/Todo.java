package com.ll.planify.domain.todo.todo.entity;

import com.ll.planify.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String content;

    private Boolean isCompleted = false;

    private LocalDate deadline;

    public static Todo addTodo(String content, LocalDate deadline) {
        Todo todo = new Todo();
        todo.content = content;
        todo.deadline = deadline;

        return todo;
    }

    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }
}