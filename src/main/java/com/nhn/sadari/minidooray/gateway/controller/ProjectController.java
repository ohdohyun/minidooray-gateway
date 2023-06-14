package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.project.*;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import com.nhn.sadari.minidooray.gateway.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final AccountService accountService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("projectRegisterDto", new ProjectRegisterDto());
        return "project/project_register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute ProjectRegisterDto projectRegisterDto, @RequestParam Long memberId) {

        IdDto result = projectService.create(projectRegisterDto);
        return "/index";
    }

    @GetMapping("/{projectId}/modify")
    public String getModifyForm(@PathVariable Long projectId, Model model) {

        ProjectGet get = projectService.findByProjectId(projectId);

        model.addAttribute("projectId", projectId);
        model.addAttribute("projectModifyDto", new ProjectModifyDto(get.getName(), get.getDescription(), get.getProjectStatus_status()));
        return "project/project_modify";
    }

    @PostMapping("/{projectId}/modify")
    public String doModify(@ModelAttribute ProjectModifyDto projectModifyDto, @PathVariable Long projectId) {
        projectService.modify(projectModifyDto, projectId);

        return "/index";
    }


    @GetMapping("/{projectId}/delete")
    public String deleteProject(@PathVariable Long projectId) {

        projectService.delete(projectId);
        return "/index";
    }

    @GetMapping("/members/{memberId}")
    public String getProjectByMemberId(@PathVariable Long memberId, Model model) {

        model.addAttribute("projectList", projectService.getProjectsByMemberId(memberId));
        return "project/project_list";
    }

    @GetMapping("/{projectId}/members/register")
    public String getProjectMemberRegisterForm(@PathVariable Long projectId, Model model) {

        model.addAttribute("projectId", projectId);
        model.addAttribute("accountList", accountService.getAccountList());

        return "project/project_member_register";
    }

    @PostMapping("/{projectId}/members/register")
    public String doProjectMemberRegister(@ModelAttribute ProjectMemberRegister projectMemberRegister, @PathVariable Long projectId) {
        projectService.createProjectMember(projectMemberRegister, projectId);
        return "/index";
    }


    @GetMapping("/{projectId}/members/{memberId}/delete")
    public String deleteProjectMemberByMemberId(@PathVariable Long projectId, @PathVariable Long memberId) {
        projectService.deleteProjectMemberByMemberId(projectId, memberId);

        return "/index";
    }

    @GetMapping("/{projectId}/members")
    public String getProjectMembers(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("projectMemberList", projectService.getProjectMemberListByProjectId(projectId));
        return "project/project_member_list";
    }

    @PostMapping("/{projectId}/members/{memberId}/modify")
    public String doProjectMemberModify(@PathVariable Long projectId, @PathVariable Long memberId, @ModelAttribute ProjectMemberModify projectMemberModify) {
        projectService.doProjectMemberModify(projectId, memberId, projectMemberModify);
        return "redirect:/projects/" + projectId + "/members";
    }


}
