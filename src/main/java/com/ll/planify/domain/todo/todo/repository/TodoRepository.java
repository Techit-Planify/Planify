package com.ll.planify.domain.todo.todo.repository;

import com.ll.planify.domain.todo.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("select "
            + "distinct q "
            + "from Todo q "
            + "where "
            + "   q.content like %:kw% ")
    Page<Todo> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}