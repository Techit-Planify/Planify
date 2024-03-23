package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.todo.todo.entity.Hashtag;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoPriority;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 키워드로 조회
    public Page<Todo> getTodosByMember(int page, String kw, Member member) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.todoRepository.findAllByKeyword(kw, member, pageable);
    }

    @Transactional
    public void completeTodo(Long todoId, Member member) {
        Optional<Todo> todoOpt = todoRepository.findByIdAndMember(todoId, member);

        todoOpt.ifPresent(todo -> {
            if (!todo.getStatus().equals(TodoStatus.완료)) {
                todo.setStatus(TodoStatus.완료);
                todoRepository.save(todo);
            }
        });
    }

    public Optional<Todo> findByTodoId(Long todoId, Member member) {
        return todoRepository.findByIdAndMember(todoId, member);
    }

    @Transactional
    public void updateTodo(Long todoId, String content, LocalDate deadline,
                           TodoPriority priority, Member member) {
        Optional<Todo> getTodo = todoRepository.findByIdAndMember(todoId, member);

        if (getTodo.isPresent()) {

            Todo todo = getTodo.get();
            todo.setContent(content);
            todo.setDeadline(deadline);
            todo.setPriority(priority);

            todoRepository.save(todo);
        }
    }

    @Transactional
    public void deleteTodo(Long todoId, Member member) {
        todoRepository.deleteByIdAndMember(todoId, member);
    }

    public String getHashtagsAsString(Long todoId, Member member) {
        Optional<Todo> todoOptional = todoRepository.findByIdAndMember(todoId, member);

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

    // 해시태그로 조회
    public Page<Todo> getTodosByMemberAndTag(int page, String kw, String tag, Member member) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.todoRepository.findByTagAndMemberAndContentContaining(tag, kw, member, pageable);
    }
}