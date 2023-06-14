package com.nhn.sadari.minidooray.gateway.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccountRegister {

    private String loginId;
    private String password;
    private String email;
    private String name;

    public void encodePassword(String encodedValue) {
        this.password = encodedValue;
    }
}
