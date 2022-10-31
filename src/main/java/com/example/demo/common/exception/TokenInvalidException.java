package com.example.demo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException(String message) {
        super(String.format(message));
    }
}
