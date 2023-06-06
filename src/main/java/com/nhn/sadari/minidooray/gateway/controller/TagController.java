package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.tag.TagRegister;
import com.nhn.sadari.minidooray.gateway.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("projects/{projectId}/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/register")
    public String getTagRegisterForm(@PathVariable Long projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("tagRegister", new TagRegister());
        return "/tag/tag_register";
    }

    @PostMapping("/register")
    public String doTagRegister(@PathVariable Long projectId, @ModelAttribute("tagRegister") TagRegister tagRegister) {

        tagService.registerTag(tagRegister, projectId);
        return "/index";
    }

    @GetMapping("/{tagId}/delete")
    public String deleteTag(@PathVariable Long projectId, @PathVariable Long tagId) {
        tagService.deleteTag(projectId, tagId);
        return "/index";
    }
}
