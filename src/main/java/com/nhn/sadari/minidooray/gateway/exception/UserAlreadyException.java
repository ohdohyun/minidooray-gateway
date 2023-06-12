package com.nhn.sadari.minidooray.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAlreadyException extends RuntimeException {
    String message;
}
