package com.devproject.booking.movie.exception;

public class OverlappingShowsException extends RuntimeException {
    public OverlappingShowsException(String message) {
        super(message);
    }
}
