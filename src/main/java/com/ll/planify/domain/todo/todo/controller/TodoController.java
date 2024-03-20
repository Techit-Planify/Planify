package com.ll.planify.domain.todo.todo.controller;

import com.ll.planify.domain.todo.todo.dto.TodoDto;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private List<TodoDto> getTodoDtos(Boolean isCompleted) {
        List<Todo> list = todoService.findByIsCompleted(isCompleted);
        return list.stream().map(todo ->
                        new TodoDto(todo.getId(), todo.getContent(), todo.getIsCompleted(),
                                todo.getCreatedAt(), todo.getUpdatedAt(), todo.getDeadline()))
                .collect(Collectors.toList());
    }

    @GetMapping("/todo")
    public String todo(Model model) {
        model.addAttribute("todoDtos", getTodoDtos(false));
        model.addAttribute("completedDtos", getTodoDtos(true));
        return "todo/main";
    }

    @GetMapping("/todo/add")
    public String addForm(@ModelAttribute("TodoDto") TodoDto todoDto) {
        return "todo/add";
    }

    @PostMapping("/todos")
    public String addTodo(@ModelAttribute("TodoDto") TodoDto todoDto) {
        Optional<Todo> addTodo = Optional.of(Todo.addTodo(
                todoDto.getContent(), todoDto.getDeadline()
        ));
        addTodo.ifPresent(todo -> todoService.save(todo));

        return "redirect:/todo";
    }
}