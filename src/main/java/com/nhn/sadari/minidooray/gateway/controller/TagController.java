package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.tag.TagDto;
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
        model.addAttribute("tagRegister", new TagDto());
        return "/tag/tag_register";
    }

    @PostMapping("/register")
    public String doTagRegister(@PathVariable Long projectId, @ModelAttribute("tagRegister") TagDto tagDto) {

        tagService.registerTag(tagDto, projectId);
        return "/index";
    }

    @GetMapping("/{tagId}/delete")
    public String deleteTag(@PathVariable Long projectId, @PathVariable Long tagId) {
        tagService.deleteTag(projectId, tagId);
        return "redirect:/projects/"+projectId+"/tags";
    }

    @GetMapping
    public String getTagList(@PathVariable Long projectId, Model model) {
        model.addAttribute("tagList", tagService.getTagList(projectId));
        return "tag/tag_list";
    }

    @PostMapping("/{tagId}/modify")
    public String modifyTag(@PathVariable Long projectId, @PathVariable Long tagId, @ModelAttribute TagDto tagDto) {
        tagService.modifyTag(projectId, tagId, tagDto);
        return "redirect:/projects/"+projectId+"/tags";
    }
}
