package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> findByIsCompleted(Boolean isCompleted) {
        return todoRepository.findByIsCompleted(isCompleted);
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }
}