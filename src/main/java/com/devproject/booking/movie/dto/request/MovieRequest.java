package com.devproject.booking.movie.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record MovieRequest(@NotBlank(message = "Title is required") String title,
                           @NotBlank(message = "Genre is required") String genre,
                           @NotNull(message = "Duration is required") Integer duration,
                           @NotBlank(message = "Language is required") String language,
                           Date releaseDate) {
}
