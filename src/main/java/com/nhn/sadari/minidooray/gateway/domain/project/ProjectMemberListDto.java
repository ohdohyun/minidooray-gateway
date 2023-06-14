package com.nhn.sadari.minidooray.gateway.domain.project;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectMemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ProjectMemberListDto {
    String memberName;
    ProjectMemberRoleType role;
    Long pk_memberId;
}
