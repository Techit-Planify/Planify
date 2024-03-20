package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    Long save(Todo todo);

    Optional<Todo> findById(Long id);

    void update(Long id, String content, LocalDate deadline);

    void changeStatus(Long id);

    List<Todo> findByIsCompleted(Boolean isCompleted);
}