package com.ll.planify.domain;

import com.ll.planify.domain.todo.todo.entity.Keyword;
import com.ll.planify.domain.todo.todo.entity.Todo;
import com.ll.planify.domain.todo.todo.service.KeywordService;
import com.ll.planify.domain.todo.todo.service.TodoService;
import com.ll.planify.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MainController {

    private final KeywordService keywordService;
    private final TodoService todoService;

    @RequestMapping("/")
    public String main(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        Long memberId = user.getId();

        List<Keyword> keywords = keywordService.getAllKeywords(memberId);
        model.addAttribute("keywords", keywords);

        List<Todo> todosInProgress = todoService.getTodosInProgress(memberId);
        model.addAttribute("todosInProgress", todosInProgress);

        double completionRate = todoService.calculateCompletionRate(memberId);
        model.addAttribute("completionRate", completionRate);

        log.info("main controller for user: {}, completionRate: {}", memberId, completionRate);
        return "domain/main";
    }
}