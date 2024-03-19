package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.dto.TodoDto;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public List<TodoDto.Response> findAllTodos() {
        List<Todo> todos = todoRepository.findAll();

        List<TodoDto.Response> responseDtoList = todos.stream().map(e -> TodoDto.Response.builder()
                .id(e.getId())
                .content(e.getContent())
                .isCompleted(e.isCompleted())
                .priority(e.getPriority())
                .deadline(e.getDeadline())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build()).collect(Collectors.toList());

        return responseDtoList;
    }
}