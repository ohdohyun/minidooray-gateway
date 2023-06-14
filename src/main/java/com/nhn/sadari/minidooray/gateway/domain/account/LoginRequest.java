package com.nhn.sadari.minidooray.gateway.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LoginRequest {
    String loginId;
    String password;
}

