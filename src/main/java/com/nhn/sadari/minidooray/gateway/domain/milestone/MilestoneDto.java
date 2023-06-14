package com.nhn.sadari.minidooray.gateway.domain.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MilestoneDto {

    private Long id;
    private String name;
    private LocalDate endDate;
    private LocalDate startDate;
}
