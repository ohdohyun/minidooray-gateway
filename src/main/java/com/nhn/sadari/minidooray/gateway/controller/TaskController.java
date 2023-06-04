package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String getTasks() {
        return "/task/task_list";
    }

    @GetMapping("/register")
    public String getTaskRegisterForm() {
        return "/task/task_register";
    }

    @GetMapping("/{id}")
    public String getTask(@PathVariable Long id) {
        return "/task/task_view";
    }
}
