package com.ll.planify.domain.todo.todo.controller;

import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import com.ll.planify.domain.todo.todo.service.HashtagService;
import com.ll.planify.domain.todo.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final HashtagService hashtagService;

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("todoForm", new TodoForm());
        return "domain/todo/todo/createTodoForm";
    }

    @PostMapping("/create")
    public String create(@Valid TodoForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "domain/todo/todo/createTodoForm";
        }

        // 날짜를 문자열로 변환하여 설정
        form.setDeadline(form.getDeadline().toString());

        // 태그 분리
        String hashtagStr = form.getTag();

        Todo todo = new Todo();
        todo.setContent(form.getContent());
        todo.setDeadline(LocalDate.parse(form.getDeadline())); // 문자열을 다시 LocalDate로 변환하여 설정
        todo.setPriority(form.getPriority());
        todo.setStatus(TodoStatus.진행중);

        todoService.save(todo);
        hashtagService.addHashtags(todo, hashtagStr);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Todo> paging = this.todoService.getTodos(page, kw);
        List<Todo> todos = paging.getContent();

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("todos", todos);
        return "domain/todo/todo/todoList";
    }

    @GetMapping("/{todoId}/complete")
    public String completeTodo(@PathVariable Long todoId) {
        todoService.completeTodo(todoId);
        return "redirect:/todo/list";
    }

    @GetMapping("/{todoId}/update")
    public String updateTodoForm(@PathVariable("todoId") Long todoId, Model model) {
        Optional<Todo> getTodo = todoService.findByTodoId(todoId);
        TodoForm form = new TodoForm();

        if (getTodo.isPresent()) {
            Todo todo = getTodo.get();
            form.setContent(todo.getContent());
            form.setDeadline(todo.getDeadline().toString()); // LocalDate를 문자열로 변환하여 설정
            form.setPriority(todo.getPriority());
            form.setTag(todoService.getHashtagsAsString(todoId));
        }

        model.addAttribute("form", form);
        return "domain/todo/todo/updateTodoForm";
    }

    @PostMapping("/{todoId}/update")
    public String updateTodo(@PathVariable Long todoId, @ModelAttribute("form") TodoForm form) {
        // 문자열로 변환된 deadline을 다시 LocalDate로 변환하여 설정
        LocalDate deadline = LocalDate.parse(form.getDeadline());

        todoService.updateTodo(todoId, form.getContent(), deadline, form.getPriority());
        hashtagService.updateHashtags(todoId, form.getTag());
        return "redirect:/todo/list";
    }

    @GetMapping("/{todoId}/delete")
    public String deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return "redirect:/todo/list";
    }
}