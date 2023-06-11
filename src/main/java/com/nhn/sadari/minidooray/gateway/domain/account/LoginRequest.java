package com.nhn.sadari.minidooray.gateway.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {
    String loginId;
    String password;
}

