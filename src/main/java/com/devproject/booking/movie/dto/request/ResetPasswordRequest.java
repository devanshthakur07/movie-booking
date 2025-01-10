package com.devproject.booking.movie.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ResetPasswordRequest(@NotEmpty(message = "New password should not be empty") String newPassword) {
}
