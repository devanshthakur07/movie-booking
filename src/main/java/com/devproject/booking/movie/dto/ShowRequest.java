package com.devproject.booking.movie.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ShowRequest(@NotNull(message = "Screen number cannot be null") Integer screenNumber,
                          @NotNull(message = "Show Time cannot be null") LocalDateTime showTime) {
}
