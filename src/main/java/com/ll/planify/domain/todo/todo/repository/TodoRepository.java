package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.todo.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIsCompleted(Boolean isCompleted);
}