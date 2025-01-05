package com.devproject.booking.movie.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ShowRequest(@NotNull(message = "Movie Id cannot be null") Integer movieId,
                          @NotNull(message = "Theater Id cannot be null") Integer theaterId,
                          @NotNull(message = "Screen number cannot be null") Integer screenNumber,
                          @NotNull(message = "Show Time cannot be null") LocalDateTime showTime) {
}
