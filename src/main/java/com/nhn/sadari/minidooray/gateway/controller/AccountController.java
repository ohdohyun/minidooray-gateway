package com.nhn.sadari.minidooray.gateway.controller;

import com.nhn.sadari.minidooray.gateway.domain.account.AccountRegister;
import com.nhn.sadari.minidooray.gateway.domain.account.AccountUpdate;
import com.nhn.sadari.minidooray.gateway.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/register")
    public String accountRegister() {

        return "/account/account_register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute AccountRegister accountRegister) {
        accountService.registerAccount(accountRegister);
        return "/index";
    }

    @GetMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return "/index";
    }

    @GetMapping("/modify/{accountId}")
    public String accountModify(@PathVariable Long accountId, Model model) {
        // 계정 정보 불러오기
        model.addAttribute("accountUpdate", accountService.getAccountUpdate(accountId));
        return "/account/account_modify";
    }

    @PostMapping("/modify/{accountId}")
    public String doModify(@PathVariable Long accountId, @ModelAttribute AccountUpdate accountUpdate) {
        accountService.doAccountUpdate(accountUpdate, accountId);
        return "/index";
    }

    @GetMapping("/{accountId}")
    public String accountUpdate(@ModelAttribute Long accountId) {

        return "/account/account_view";
    }
}
