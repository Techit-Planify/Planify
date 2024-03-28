package com.ll.planify.domain.todo.todo.controller;

import com.ll.planify.domain.member.member.entity.Member;
import com.ll.planify.domain.member.member.repository.MemberRepository;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.entity.TodoStatus;
import com.ll.planify.domain.todo.todo.service.HashtagService;
import com.ll.planify.domain.todo.todo.service.TodoService;
import com.ll.planify.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final HashtagService hashtagService;
    private final MemberRepository memberRepository;

    @GetMapping("/create")
    public String createForm(Model model) {
        TodoForm todoForm = new TodoForm();
        model.addAttribute("todoForm", todoForm);
        return "domain/todo/todo/createTodoForm";
    }

    @PostMapping("/create")
    public String create(@Valid TodoForm form, BindingResult result,
                         @AuthenticationPrincipal CustomUserDetails user) {

        if (result.hasErrors()) {
            return "domain/todo/todo/createTodoForm";
        }

        Optional<Member> opMember = memberRepository.findById(user.getId());
        if (opMember.isEmpty()) {
            return "redirect:/";
        }
        Member member = opMember.get();

        // 날짜를 문자열로 변환하여 설정
        form.setDeadline(form.getDeadline().toString());

        Todo todo = new Todo();
        todo.setContent(form.getContent());
        todo.setDeadline(LocalDateTime.parse(form.getDeadline())); // 문자열 -> LocalDateTime 변환
        todo.setPriority(form.getPriority());
        todo.setStatus(TodoStatus.진행중);
        todo.setMember(member);

        todoService.save(todo);

        // 태그가 있다면 태그 분리해서 등록
        String hashtagStr = form.getTag();
        if (hashtagStr != null && !hashtagStr.trim().isEmpty()) {
            hashtagService.addHashtags(todo, hashtagStr);
        }
        return "redirect:/?todoCreated=true";
    }

    @GetMapping("/{todoId}/detail")
    public String showTodoDetail(@PathVariable Long todoId, Model model,
                                 @AuthenticationPrincipal CustomUserDetails user) {
        Optional<Member> opMember = memberRepository.findById(user.getId());

        if (opMember.isEmpty()) {
            return "redirect:/";
        }
        Member member = opMember.get();
        Optional<Todo> optTodo = todoService.findByTodoId(todoId, member);

        if (optTodo.isPresent()) {
            Todo todo = optTodo.get();
            model.addAttribute("todo", todo);
        } else {
            return "redirect:/todo/list";
        }
        return "domain/todo/todo/todoDetail";
    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw,
                       @RequestParam(value = "tag", required = false) String tag,
                       @RequestParam(value = "status", required = false) TodoStatus status,
                       @AuthenticationPrincipal CustomUserDetails user) {

        return memberRepository.findById(user.getId())
                .map(member -> processTodoList(model, kw, tag, status, member))
                .orElse("redirect:/");
    }

    private String processTodoList(Model model, String kw, String tag,
                                   TodoStatus status, Member member) {

        List<Todo> todos = todoService.getTodosByCriteria(kw, tag, status, member);

        model.addAttribute("todos", todos);
        model.addAttribute("tag", tag);
        model.addAttribute("kw", kw);
        model.addAttribute("status", status);

        return "domain/todo/todo/todoList";
    }

    @GetMapping("/{todoId}/complete")
    public String completeTodo(@PathVariable Long todoId, @AuthenticationPrincipal CustomUserDetails user) {
        Optional<Member> opMember = memberRepository.findById(user.getId());

        if (opMember.isEmpty()) {
            return "redirect:/";
        }
        Member member = opMember.get();

        todoService.completeTodo(todoId, member);
        return "redirect:/?todoCompleted=true";
    }

    @GetMapping("/{todoId}/update")
    public String updateTodoForm(@PathVariable("todoId") Long todoId, Model model,
                                 @AuthenticationPrincipal CustomUserDetails user) {
        Optional<Member> opMember = memberRepository.findById(user.getId());

        if (opMember.isEmpty()) {
            return "redirect:/";
        }
        Member member = opMember.get();

        Optional<Todo> getTodo = todoService.findByTodoId(todoId, member);
        TodoForm form = new TodoForm();

        if (getTodo.isPresent()) {
            Todo todo = getTodo.get();
            form.setContent(todo.getContent());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            form.setDeadline(todo.getDeadline().format(formatter));

            form.setPriority(todo.getPriority());
            form.setTag(todoService.getHashtagsAsString(todoId, member));
        }

        model.addAttribute("form", form);
        return "domain/todo/todo/updateTodoForm";
    }

    @PostMapping("/{todoId}/update")
    public String updateTodo(@PathVariable Long todoId, @ModelAttribute("form") TodoForm form,
                             @AuthenticationPrincipal CustomUserDetails user) {
        Optional<Member> opMember = memberRepository.findById(user.getId());

        if (opMember.isEmpty()) {
            return "redirect:/";
        }
        Member member = opMember.get();

        // 문자열 deadline -> LocalDateTime 변환
        LocalDateTime deadline = LocalDateTime.parse(form.getDeadline());

        todoService.updateTodo(todoId, form.getContent(), deadline, form.getPriority(), member);
        hashtagService.updateHashtags(todoId, form.getTag());
        return "redirect:/todo/list";
    }

    @GetMapping("/{todoId}/delete")
    public String deleteTodo(@PathVariable Long todoId, @AuthenticationPrincipal CustomUserDetails user) {
        Optional<Member> opMember = memberRepository.findById(user.getId());

        if (opMember.isEmpty()) {
            return "redirect:/";
        }
        Member member = opMember.get();

        todoService.deleteTodo(todoId, member);
        return "redirect:/todo/list";
    }
}