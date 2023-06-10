package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class TestController {
    @GetMapping("/test")
    public String getTestPage() {

        return "test/test_page";
    }

    @GetMapping(value = {"/index", "/"})
    public String getHome(HttpServletRequest request, HttpServletResponse response) {





        return "index";
    }

}
