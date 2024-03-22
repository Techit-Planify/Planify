package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.todo.todo.entity.Hashtag;
import com.ll.planify.domain.todo.todo.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByTodoIdAndKeyword(Long todoId, Keyword keyword);

    void removeHashtagsByTodoId(Long todoId);
}