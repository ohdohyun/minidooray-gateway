package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {

    @GetMapping("/register")
    public String getMilestoneRegisterForm() {

        return "/milestone/milestone_register";
    }

    @GetMapping
    public String getMilestoneList() {

        return "/milestone/milestone_list";
    }
}
