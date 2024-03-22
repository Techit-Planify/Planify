package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Hashtag;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoPriority;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    // 목록 조회 - 페이징
    public Page<Todo> getTodos(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.todoRepository.findAllByKeyword(kw, pageable);
    }

    // 목록 조회 - 내용
    public List<Todo> findTodos() {
        return todoRepository.findAll();
    }

    @Transactional
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

    private Specification<Todo> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Todo> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); // 중복 제거
                return cb.or(cb.like(q.get("content"), "%" + kw + "%"));
            }
        };
    }
}