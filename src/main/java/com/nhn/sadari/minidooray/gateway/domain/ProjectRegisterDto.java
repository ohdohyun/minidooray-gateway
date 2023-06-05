package com.nhn.sadari.minidooray.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectRegisterDto {

    String name;
    String description;
    Long memberId;
    String memberName;
}
