package com.nhn.sadari.minidooray.gateway.domain.project;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectMemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class ProjectMemberModify {
    private ProjectMemberRoleType role;
}
