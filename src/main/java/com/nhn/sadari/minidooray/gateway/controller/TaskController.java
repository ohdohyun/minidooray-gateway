package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projects/{id}/tasks")
public class TaskController {

    @GetMapping
    public String getTasks(@PathVariable Long id) {
        return "/task/task_list";
    }

    @GetMapping("/register")
    public String getTaskRegisterForm(@PathVariable Long id) {
        return "/task/task_register";
    }

    @GetMapping("/{taskId}")
    public String getTask(@PathVariable Long id, @PathVariable Long taskId) {
        return "/task/task_view";
    }
}
