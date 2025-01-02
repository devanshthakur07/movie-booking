package com.devproject.booking.movie.dto;

public record RegisterRequest(String username, String password, String email, String role) {
}
