package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/test")
    public String getTestPage() {

        return "test/test_page";
    }

    @GetMapping("/index")
    public String getHome() {
        return "redirect:/index";
    }
}
