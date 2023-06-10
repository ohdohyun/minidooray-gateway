package com.nhn.sadari.minidooray.gateway.domain.account;

import com.nhn.sadari.minidooray.gateway.enumclass.MemberStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {
    Long accountId;
    String loginId;
    String password;
    String username;
    MemberStatusType status;
}
