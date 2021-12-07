package com.justinalmassi.backend.error_handling;

public class SortByException extends RuntimeException{
    public SortByException() {
        super("Invalid SortBy tag");
    }
}
