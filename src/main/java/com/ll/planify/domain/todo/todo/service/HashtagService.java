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
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        keywordContents.forEach(keywordContent ->
                saveHashtag(todo, keywordContent));
    }

    @Transactional
    public void saveHashtag(Todo todo, String keywordContent) {
        Keyword keyword = keywordService.save("#" + keywordContent);

        hashtagRepository.findByTodoIdAndKeyword(todo.getId(), keyword)
                .orElseGet(() -> {
                    Hashtag newHashtag = new Hashtag();
                    newHashtag.setTodo(todo);
                    newHashtag.setKeyword(keyword);
                    return hashtagRepository.save(newHashtag);
                });
    }

    // 해시태그 수정 및 삭제
    @Transactional
    public void updateHashtags(Long todoId, String keywordContentsStr) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        Long memberId = todo.getMember().getId();
        hashtagRepository.removeHashtagsByTodoId(todoId);
        addHashtags(todo, keywordContentsStr);
        removeUnusedKeywords(memberId); // 더 이상 사용되지 않는 키워드 삭제
    }

    @Transactional
    public void removeUnusedKeywords(Long memberId) {
        List<Keyword> allKeywords = keywordService.getAllKeywords(memberId);
        allKeywords.forEach(keyword -> {
            if (hashtagRepository.findByKeywordId(keyword.getId()).isEmpty()) {
                keywordService.deleteKeyword(keyword.getId());
            }
        });
    }
}