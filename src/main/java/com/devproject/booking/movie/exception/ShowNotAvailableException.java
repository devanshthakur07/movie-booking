package com.devproject.booking.movie.exception;

public class ShowNotAvailableException extends RuntimeException {
    public ShowNotAvailableException(String message) {
        super(message);
    }
}
