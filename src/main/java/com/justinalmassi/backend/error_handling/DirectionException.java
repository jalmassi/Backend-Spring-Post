package com.justinalmassi.backend.error_handling;

public class DirectionException extends RuntimeException{
    public DirectionException() {
        super("Invalid Direction tag");
    }
}
