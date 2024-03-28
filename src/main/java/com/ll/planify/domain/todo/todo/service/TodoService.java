package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.todo.todo.entity.Hashtag;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoPriority;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<Todo> getTodosByCriteria(String kw, String tag,
                                         TodoStatus status, Member member) {

        return todoRepository.findAllByCriteria(kw, tag, status, member);
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
    public void updateTodo(Long todoId, String content, LocalDateTime deadline,
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

    public List<Todo> getTodosInProgress(Long memberId) {
        return todoRepository.findByMemberIdAndStatus(memberId, TodoStatus.진행중);
    }

    public double calculateCompletionRate(Long memberId) {
        long totalTasks = todoRepository.countByMemberId(memberId);
        long completedTasks = todoRepository.countByMemberIdAndStatus(memberId, TodoStatus.완료);
        return (totalTasks > 0) ? ((double) completedTasks / totalTasks) * 100 : 0;
    }
}