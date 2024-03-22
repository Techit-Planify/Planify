package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Hashtag;
import com.ll.planify.domain.todo.todo.entity.Keyword;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.repository.HashtagRepository;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final KeywordService keywordService;
    private final HashtagRepository hashtagRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public void addHashtags(Todo todo, String keywordContentsStr) {
        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        keywordContents.forEach(keywordContent ->
                saveHashtag(todo, keywordContent));
    }

    @Transactional
    public Hashtag saveHashtag(Todo todo, String keywordContent) {
        Keyword keyword = keywordService.save("#" + keywordContent);

        Optional<Hashtag> opHashtag = hashtagRepository.findByTodoIdAndKeyword(todo.getId(), keyword);

        return opHashtag.orElseGet(() -> {
            Hashtag hashtag = Hashtag.builder()
                    .todo(todo)
                    .keyword(keyword)
                    .build();
            return hashtagRepository.save(hashtag);
        });
    }

    @Transactional
    public void updateHashtags(Long todoId, String keywordContentsStr) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);

        optionalTodo.ifPresent(todo -> {
            hashtagRepository.removeHashtagsByTodoId(todoId);

            addHashtags(todo, keywordContentsStr);
        });
    }
}