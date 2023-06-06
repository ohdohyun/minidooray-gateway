package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.task.TaskRegister;
import com.nhn.sadari.minidooray.gateway.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/register")
    public String getTaskRegisterForm(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskRegister", new TaskRegister());
        return "/task/task_register";
    }

    @PostMapping("/register")
    public String doTaskRegister(@PathVariable Long projectId, @ModelAttribute("taskRegister") TaskRegister taskRegister) {

        taskService.registerTask(taskRegister, projectId);
        return "/index";
    }

    @GetMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteTask(projectId, taskId);
        return "/index";
    }
}
