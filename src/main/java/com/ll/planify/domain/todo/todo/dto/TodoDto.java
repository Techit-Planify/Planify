package com.ll.planify.domain.todo.todo.dto;

import java.time.LocalDate;

public class TodoDto {

    private Long id;
    private String content;
    private boolean completed;
    private int priority;
    private LocalDate deadline;
}