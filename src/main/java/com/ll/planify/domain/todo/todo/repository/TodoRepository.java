package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.todo.todo.entity.Todo;
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
}