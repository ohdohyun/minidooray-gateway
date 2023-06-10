package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.*;
import com.nhn.sadari.minidooray.gateway.domain.project.ProjectGet;
import com.nhn.sadari.minidooray.gateway.domain.project.ProjectMemberRegister;
import com.nhn.sadari.minidooray.gateway.domain.project.ProjectModifyDto;
import com.nhn.sadari.minidooray.gateway.domain.project.ProjectRegisterDto;
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


    // 등록 #TODO 리턴할 때 projects로 가는걸 projects/{memberId} 로 가게 하기
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("projectRegisterDto", new ProjectRegisterDto());
        return "project/project_register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute ProjectRegisterDto projectRegisterDto) {

        // #Todo 로그인 이후 고치기
        projectRegisterDto.setMemberId(1L);
        projectRegisterDto.setMemberName("홍길동");

        IdDto result = projectService.create(projectRegisterDto);
        return "/index";
    }

    @GetMapping("/{projectId}/modify")
    public String getModifyForm(@PathVariable Long projectId, Model model) {

        ProjectGet get = projectService.findByProjectId(projectId);
        ProjectModifyDto projectModifyDto = new ProjectModifyDto();
        projectModifyDto.setName(get.getName());
        projectModifyDto.setDescription(get.getDescription());
        projectModifyDto.setStatus(get.getProjectStatus_status());

        model.addAttribute("projectId", projectId);
        model.addAttribute("projectModifyDto", projectModifyDto);
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

}
