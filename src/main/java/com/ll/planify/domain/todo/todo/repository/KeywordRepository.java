package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.todo.todo.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findByContent(String keywordContent);

    @Query("SELECT " +
            "DISTINCT h.keyword " +
            "FROM " +
            "Hashtag h " +
            "JOIN " +
            "h.todo t " +
            "WHERE " +
            "t.member.id = :memberId")
    List<Keyword> findKeywordById(Long memberId);
}