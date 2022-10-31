package com.example.demo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UploadFailedException extends RuntimeException {

    public UploadFailedException(String message) {
        super(String.format(message));
    }
}
