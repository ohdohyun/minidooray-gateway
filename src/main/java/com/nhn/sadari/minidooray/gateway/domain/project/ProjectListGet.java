package com.nhn.sadari.minidooray.gateway.domain.project;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectStatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProjectListGet {

    private Long id;
    private String name;
    private String description;
    private ProjectStatusType projectStatus_status;

    @Builder
    public ProjectListGet(Long id, String name, String description, ProjectStatusType projectStatus_status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectStatus_status = projectStatus_status;
    }
}
