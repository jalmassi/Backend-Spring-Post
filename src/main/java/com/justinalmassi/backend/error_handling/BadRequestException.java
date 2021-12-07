package com.justinalmassi.backend.error_handling;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad Request");
    }
}
