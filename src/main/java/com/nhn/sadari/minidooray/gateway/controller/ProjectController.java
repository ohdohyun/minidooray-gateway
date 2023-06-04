package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    public String getProjects(Model model) {

        return "project/project_list";
    }

    @GetMapping("/{id}")
    public String getProjectInfo(@PathVariable Long id) {

        return "/project/project_view";
    }

    @GetMapping("/update")
    public String getProjectUpdateForm() {
        return "/project/project_update";
    }

    @GetMapping("/{id}/members")
    public String getProjectMemberList(@PathVariable Long id) {

        return "/project/project_member_list";
    }
}
