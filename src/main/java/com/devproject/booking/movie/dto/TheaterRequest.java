package com.devproject.booking.movie.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TheaterRequest(@NotBlank(message = "Theater name cannot be blank") String name,
                             @NotBlank(message = "Theater city cannot be blank")String city,
                             @NotNull(message = "Theater screens cannot be null") Integer screens) {
}
