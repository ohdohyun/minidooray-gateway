package com.nhn.sadari.minidooray.gateway.converter;

import com.nhn.sadari.minidooray.gateway.enumclass.ProjectMemberRoleType;
import org.springframework.core.convert.converter.Converter;

public class StringToProjectMemberRoleTypeConverter implements Converter<String, ProjectMemberRoleType> {
    @Override
    public ProjectMemberRoleType convert(String source) {
        try {
            return ProjectMemberRoleType.valueOf(source);
        } catch (Exception e) {
            return null; // or throw an exception
        }
    }
}