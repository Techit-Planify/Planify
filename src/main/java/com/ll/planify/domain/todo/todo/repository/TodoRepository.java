package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndMember(Long id, Member member);

    void deleteByIdAndMember(Long id, Member member);

    @Query("select "
            + "distinct q "
            + "from Todo q "
            + "where "
            + "   q.content "
            + "like %:kw% "
            + "and q.member = :member")
    Page<Todo> findAllByKeyword(@Param("kw") String kw, Member member, Pageable pageable);

    @Query("select t "
            + "FROM Todo t "
            + "JOIN t.hashtags h "
            + "WHERE "
            + "h.keyword.content = :tag "
            + "AND t.member = :member "
            + "AND t.content "
            + "LIKE %:kw%")
    Page<Todo> findByTagAndMemberAndContentContaining(String tag, String kw, Member member, Pageable pageable);

    @Query("SELECT t "
            + "FROM Todo t "
            + "LEFT JOIN t.hashtags h "
            + "ON (:tag IS NULL OR h.keyword.content = :tag) "
            + "WHERE "
            + "(:kw IS NULL OR t.content LIKE %:kw%) "
            + "AND (:status IS NULL OR t.status = :status) "
            + "AND t.member = :member "
            + "GROUP BY t.id "
    )
    Page<Todo> findAllByCriteria(@Param("kw") String kw,
                                 @Param("tag") String tag,
                                 @Param("status") TodoStatus status,
                                 @Param("member") Member member,
                                 Pageable pageable);
}