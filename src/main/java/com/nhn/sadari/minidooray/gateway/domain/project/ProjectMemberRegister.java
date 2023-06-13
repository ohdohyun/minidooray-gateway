package com.nhn.sadari.minidooray.gateway.domain.project;

import lombok.*;

@Getter
public class ProjectMemberRegister {
    private Long memberId;
    private String memberName;

    public ProjectMemberRegister(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }
}
