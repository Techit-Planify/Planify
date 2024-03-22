package com.ll.planify.domain.todo.todo.service;

import com.ll.planify.domain.todo.todo.entity.Keyword;
import com.ll.planify.domain.todo.todo.repository.KeywordRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public Keyword save(String keywordContent) {
        Optional<Keyword> optKeyword = keywordRepository.findByContent(keywordContent);

        if (optKeyword.isPresent()) {
            return optKeyword.get();
        }

        Keyword keyword = Keyword
                .builder()
                .content(keywordContent)
                .build();

        keywordRepository.save(keyword);

        return keyword;
    }

    public List<Keyword> getAllKeywords() {
        return keywordRepository.findAll();
    }
}