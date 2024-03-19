package com.ll.planify;

import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PlanifyApplicationTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("할 일 생성")
    public void testSaveTodo() {
        // 새로운 할 일 생성
        Todo todo = Todo.builder()
                .content("Study for exam")
                .isCompleted(false)
                .priority(1)
                .deadline(LocalDate.of(2024, 3, 25))
                .build();

        Todo savedTodo = todoRepository.save(todo);

        // 저장된 할 일이 null이 아닌지 확인
        assertNotNull(savedTodo.getId());

        // 저장된 할 일의 내용과 우선순위 확인
        assertEquals("Study for exam", savedTodo.getContent());
        assertEquals(1, savedTodo.getPriority());
    }
}
