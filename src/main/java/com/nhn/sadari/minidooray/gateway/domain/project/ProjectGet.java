package com.nhn.sadari.minidooray.gateway.domain.project;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectGet {
    private String name;
    private String description;
    private ProjectStatusType projectStatus_status;

    @Builder
    public ProjectGet(String name, String description, ProjectStatusType projectStatus_status) {
        this.name = name;
        this.description = description;
        this.projectStatus_status = projectStatus_status;
    }
}
