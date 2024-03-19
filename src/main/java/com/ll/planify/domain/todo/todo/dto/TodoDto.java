package com.ll.planify.domain.todo.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ll.planify.domain.todo.todo.entity.Todo;
import lombok.*;
import org.apache.coyote.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private String content;
        private boolean isCompleted;
        private int priority;
        private LocalDate deadline;

        @Builder
        public Request(String content, boolean isCompleted, int priority, LocalDate deadline) {
            this.content = content;
            this.isCompleted = isCompleted;
            this.priority = priority;
            this.deadline = deadline;
        }

        // DB에 저장하기 위한 DTO -> Entity 변환 메소드
        public Todo toEntity() {
            return Todo.builder()
                    .content(content)
                    .isCompleted(isCompleted)
                    .priority(priority)
                    .deadline(deadline)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long id;
        private String content;
        private boolean isCompleted;
        private int priority;
        private LocalDate deadline;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime updatedAt;

        @Builder
        public Response(Long id, String content, boolean isCompleted, int priority, LocalDate deadline,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
            this.id = id;
            this.content = content;
            this.isCompleted = isCompleted;
            this.priority = priority;
            this.deadline = deadline;
        }

        // DB 조회를 위한 Entity -> DTO 변환 메소드
        public static TodoDto.Response of(Todo todo) {
            return Response.builder()
                    .id(todo.getId())
                    .content(todo.getContent())
                    .isCompleted(todo.isCompleted())
                    .priority(todo.getPriority())
                    .deadline(todo.getDeadline())
                    .createdAt(todo.getCreatedAt())
                    .updatedAt(todo.getUpdatedAt())
                    .build();
        }
    }
}