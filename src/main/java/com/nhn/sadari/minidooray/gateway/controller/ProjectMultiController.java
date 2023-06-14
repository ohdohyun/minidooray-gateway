package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.service.MilestoneService;
import com.nhn.sadari.minidooray.gateway.service.ProjectService;
import com.nhn.sadari.minidooray.gateway.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectMultiController {

    private final ProjectService projectService;
    private final MilestoneService milestoneService;
    private final TagService tagService;

    @GetMapping("/{projectId}")
    public String getProjectTotalInfo(@PathVariable Long projectId, Model model) {

        model.addAttribute("projectMemberList", projectService.getProjectMemberListByProjectId(projectId));
        model.addAttribute("milestoneList", milestoneService.getMilestoneList(projectId));
        model.addAttribute("tagList", tagService.getTagList(projectId));

        return "project/project_view";
    }
}
