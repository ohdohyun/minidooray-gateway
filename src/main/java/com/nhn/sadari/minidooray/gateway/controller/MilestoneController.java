package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("projects/{id}/milestones")
public class MilestoneController {

    @GetMapping("/register")
    public String getMilestoneRegisterForm(@PathVariable Long id) {

        return "/milestone/milestone_register";
    }

    @GetMapping
    public String getMilestoneList(@PathVariable Long id) {

        return "/milestone/milestone_list";
    }
}
