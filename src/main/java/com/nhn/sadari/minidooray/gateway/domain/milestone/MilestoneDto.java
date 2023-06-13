package com.nhn.sadari.minidooray.gateway.domain.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class MilestoneDto {
    private String name;
    private LocalDate endDate;
    private LocalDate startDate;
}
