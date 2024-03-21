package com.ll.planify.domain.todo.todo.controller;

import com.ll.planify.domain.todo.todo.entity.TodoPriority;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TodoForm {

    @NotEmpty(message = "내용은 필수로 작성해야 합니다.")
    private String content;

    private String deadline;

    private TodoPriority priority; // 우선순위 [높음, 중간, 낮음]
}