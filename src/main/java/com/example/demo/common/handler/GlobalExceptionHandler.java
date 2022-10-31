package com.example.demo.common.handler;

import com.example.demo.common.exception.TokenInvalidException;
import com.example.demo.model.response._MessageErrorResponse;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TokenInvalidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public _MessageErrorResponse handleRefreshTokenException(
            TokenInvalidException ex, WebRequest request) {
        return new _MessageErrorResponse(new Date(), HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }
}
