package com.ll.planify.domain.todo.todo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TodoDto {

    private Long id;

    private String content;

    private Boolean isCompleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "해당 날짜는 마감일로 설정할 수 없습니다.")
    private LocalDate deadline;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public TodoDto(Long id, String content, Boolean isCompleted,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDate deadline
    ) {
        this.id = id;
        this.content = content;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deadline = deadline;
    }
}