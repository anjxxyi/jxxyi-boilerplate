package com.anjxxyi.practice.boilerplate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/")
    public String main() {
        return "index";
    }
}
