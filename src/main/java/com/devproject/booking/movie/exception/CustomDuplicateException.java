package com.devproject.booking.movie.exception;

public class CustomDuplicateException extends RuntimeException {
    public CustomDuplicateException(String message) {
        super(message);
    }
}
