package com.nhn.sadari.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String content;
    private LocalDate endDate;
    private Long writerId;
    private LocalDateTime createdAt;

    private List<Long> memberIds;;

    private Long milestoneId;

    private List<Long> tagIds;

    private String writerName;

    private List<String> memberNames;

    private List<String> tagNames;


}