package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tags")
public class TagController {

    @GetMapping
    public String getTags() {
        return "/tag/tag_list";
    }

    @GetMapping("/register")
    public String getTagRegisterForm() {
        return "/tag/tag_register";
    }

    @PostMapping("/register")
    public String doTagRegister(@RequestParam("tagName") String tagName) {
        System.out.println("is in?");
        System.out.println(tagName);
        return "redirect:/test";
    }
}
