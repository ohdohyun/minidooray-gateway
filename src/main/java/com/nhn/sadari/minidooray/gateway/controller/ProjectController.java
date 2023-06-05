package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.IdDto;
import com.nhn.sadari.minidooray.gateway.domain.ProjectModifyDto;
import com.nhn.sadari.minidooray.gateway.domain.ProjectModifyGet;
import com.nhn.sadari.minidooray.gateway.domain.ProjectRegisterDto;
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

        ProjectModifyGet get = projectService.findByProjectId(projectId);
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
