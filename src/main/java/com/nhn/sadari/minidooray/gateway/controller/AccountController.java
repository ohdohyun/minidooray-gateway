package com.nhn.sadari.minidooray.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/register")
    public String accountRegister() {

        return "/account/account_register";
    }

    @GetMapping("/{id}")
    public String accountRegister(@PathVariable Long id) {

        return "/account/account_view";
    }
}
