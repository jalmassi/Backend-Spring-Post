package com.justinalmassi.backend.error_handling;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String exMessage) {
        super(exMessage);
    }
}
