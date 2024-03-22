package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Hashtag;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoPriority;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
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
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public Long save(Todo todo) {
        todoRepository.save(todo);

        return todo.getId();
    }

    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }

    public void completeTodo(Long todoId) {
        Optional<Todo> todoOptional = todoRepository.findById(todoId);

        todoOptional.ifPresent(todo -> {
            if (!todo.getStatus().equals(TodoStatus.완료)) {
                todo.setStatus(TodoStatus.완료);
                todoRepository.save(todo);
            }
        });
    }

    public Optional<Todo> findByTodoId(Long todoId) {
        return todoRepository.findById(todoId);
    }

    @Transactional
    public void updateTodo(Long todoId, String content, LocalDate deadline, TodoPriority priority) {
        Optional<Todo> getTodo = todoRepository.findById(todoId);

        if (getTodo.isPresent()) {

            Todo todo = getTodo.get();
            todo.setContent(content);
            todo.setDeadline(deadline);
            todo.setPriority(priority);

            todoRepository.save(todo);
        }
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    public String getHashtagsAsString(Long todoId) {
        Optional<Todo> todoOptional = todoRepository.findById(todoId);
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            List<Hashtag> hashtags = todo.getHashtags();
            StringBuilder stringBuilder = new StringBuilder();
            for (Hashtag hashtag : hashtags) {
                stringBuilder.append(hashtag.getKeyword().getContent()).append(" ");
            }
            return stringBuilder.toString().trim();
        }
        return "";
    }
}