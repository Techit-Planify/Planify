package com.ll.planify.domain.todo.todo.controller;

import com.ll.planify.domain.todo.todo.dto.TodoDto;
import com.ll.planify.domain.todo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/")
    public String findAllTodos(Model model) {
        List<TodoDto.Response> todos = todoService.findAllTodos();
        log.info("todos count = {}", todos.size());
        model.addAttribute("todos", todos);
        return "todos";
    }

    @PostMapping("/addTodo")
    @PreAuthorize("isAuthenticated()")
    public String addTodo(@ModelAttribute TodoDto.Request request) {
        // TodoDto.Request 객체를 통해 할 일을 추가하고 추가된 할 일의 ID를 받아옴
        Long todoId = todoService.addTodo(request);
        log.info("Added todo with ID = {}", todoId);

        return "redirect:/";
    }
}