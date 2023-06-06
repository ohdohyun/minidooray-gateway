package com.nhn.sadari.minidooray.gateway.domain.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentRegister {
    private Long writerId;
    private String contents;
}
