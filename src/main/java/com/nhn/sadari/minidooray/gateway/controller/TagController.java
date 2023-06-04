package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("projects/{id}/tags")
public class TagController {

    @GetMapping
    public String getTags(@PathVariable Long id) {
        return "/tag/tag_list";
    }

    @GetMapping("/register")
    public String getTagRegisterForm(@PathVariable Long id) {
        return "/tag/tag_register";
    }

    @PostMapping("/register")
    public String doTagRegister(@PathVariable Long id ,@RequestParam("tagName") String tagName) {
        System.out.println("is in?");
        System.out.println(tagName);
        return "redirect:/test";
    }
}
