package com.devproject.booking.movie.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(@NotBlank(message = "email cannot be empty") String email) {
}
