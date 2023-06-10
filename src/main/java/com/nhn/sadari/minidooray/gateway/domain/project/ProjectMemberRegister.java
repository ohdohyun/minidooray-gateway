package com.nhn.sadari.minidooray.gateway.domain.project;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectMemberRoleType;
import lombok.*;

@Getter
public class ProjectMemberRegister {
    private Long memberId;
    private String memberName;
    private ProjectMemberRoleType role;

    public ProjectMemberRegister(Long memberId, String memberName, ProjectMemberRoleType role) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.role = role;
    }
}
