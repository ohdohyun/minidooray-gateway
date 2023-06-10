package com.nhn.sadari.minidooray.gateway.domain.account;

import com.nhn.sadari.minidooray.gateway.enumclass.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AccountModify {

    private String loginId;
    private String password;
    private String email;
    private String name;
    private MemberStatusType status;
}
