package com.nhn.sadari.minidooray.gateway.domain;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectModifyGet {
    private String name;
    private String description;
    private ProjectStatusType projectStatus_status;
}
