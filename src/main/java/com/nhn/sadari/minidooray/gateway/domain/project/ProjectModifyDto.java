package com.nhn.sadari.minidooray.gateway.domain.project;


import com.nhn.sadari.minidooray.gateway.enumclass.ProjectStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModifyDto {
    private String name;
    private String description;
    private ProjectStatusType status;
}
