package com.nhn.sadari.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TaskRegister {
    private String title;
    private String content;
    private Long writerId;
    private LocalDate endDate;
    private List<Long> memberIds;
    private Long milestoneId;
    private List<Long> tagIds;
}
