package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndMember(Long id, Member member);

    void deleteByIdAndMember(Long id, Member member);

    @Query("SELECT t FROM Todo t LEFT JOIN t.hashtags h ON h.keyword.content = :tag " +
            "WHERE t.member = :member " +
            "AND (:tag IS NULL OR h.keyword.content IS NOT NULL) " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:kw IS NULL OR t.content LIKE %:kw%) GROUP BY t.id")
    List<Todo> findAllByCriteria(@Param("kw") String kw,
                                 @Param("tag") String tag,
                                 @Param("status") TodoStatus status,
                                 @Param("member") Member member);

    List<Todo> findByMemberIdAndStatus(Long memberId, TodoStatus status);

    long countByMemberId(Long memberId);

    long countByMemberIdAndStatus(Long memberId, TodoStatus status);
}