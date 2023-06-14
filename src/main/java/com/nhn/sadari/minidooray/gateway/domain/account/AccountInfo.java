package com.nhn.sadari.minidooray.gateway.domain.account;

import com.nhn.sadari.minidooray.gateway.enumclass.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AccountInfo {

    private String loginId;
    private String password;
    private String email;
    private String name;
    private MemberStatusType status;
}
