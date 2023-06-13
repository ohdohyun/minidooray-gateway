package com.nhn.sadari.minidooray.gateway.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountListDto {

    private Long id;
    private String name;
    private String email;
}
