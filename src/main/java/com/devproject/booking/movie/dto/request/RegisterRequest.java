package com.devproject.booking.movie.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank(message = "Username is required") String username,
                              @NotBlank(message = "Password is required") String password,
                              @Email(message = "Email must be valid") @NotBlank(message = "Email is required") String email,
                              @NotBlank(message = "Role is required") String role) {
}
