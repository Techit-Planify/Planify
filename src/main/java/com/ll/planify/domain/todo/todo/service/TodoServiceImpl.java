package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    @Transactional
    public Long save(Todo todo) {
        return todoRepository.save(todo).getId();
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    @Transactional
    public void update(Long id, String content, LocalDate deadline) {
        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(t -> t.update(content, deadline));

    }

    @Override
    public void changeStatus(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(toDo -> toDo.changeStatus());
    }

    @Override
    public List<Todo> findByIsCompleted(Boolean isCompleted) {
        return todoRepository.findByIsCompleted(isCompleted);
    }
}