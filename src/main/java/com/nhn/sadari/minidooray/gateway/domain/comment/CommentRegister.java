package com.nhn.sadari.minidooray.gateway.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRegister {
    private Long writerId;
    private String contents;
}
