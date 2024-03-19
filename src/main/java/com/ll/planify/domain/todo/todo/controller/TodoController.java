package com.ll.planify.domain.todo.todo.controller;

import com.ll.planify.domain.todo.todo.dto.TodoDto;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
}