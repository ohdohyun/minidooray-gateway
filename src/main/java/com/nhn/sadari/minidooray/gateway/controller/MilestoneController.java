package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.milestone.MilestoneRegister;
import com.nhn.sadari.minidooray.gateway.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("projects/{projectId}/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping("/register")
    public String getMilestoneRegisterForm(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("milestoneRegister", new MilestoneRegister());
        return "/milestone/milestone_register";
    }

    @PostMapping("/register")
    public String doMilestoneRegister(@ModelAttribute MilestoneRegister milestoneRegister, @PathVariable Long projectId) {
        LocalDate test = milestoneRegister.getStartDate();
        milestoneService.registerMilestone(milestoneRegister, projectId);
        return "/index";
    }

    @GetMapping("/{milestoneId}/delete")
    public String deleteMilestone(@PathVariable Long projectId, @PathVariable Long milestoneId) {
        milestoneService.deleteMilestone(projectId, milestoneId);
        return "/index";
    }

}
