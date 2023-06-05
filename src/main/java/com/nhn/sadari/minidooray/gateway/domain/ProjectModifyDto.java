package com.nhn.sadari.minidooray.gateway.domain;


import com.nhn.sadari.minidooray.gateway.enumclass.ProjectStatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectModifyDto {
    private String name;
    private String description;
    private ProjectStatusType status;
}
