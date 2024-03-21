package com.ll.planify.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class MainController {

    @RequestMapping("/")
    public String main() {
        log.info("main controller");
        return "domain/main";
    }
}