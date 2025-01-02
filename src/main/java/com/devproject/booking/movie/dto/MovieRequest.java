package com.devproject.booking.movie.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record MovieRequest(@NotBlank(message = "Title is required") String title,
                           @NotBlank(message = "Genre is required") String genre,
                           @NotBlank(message = "Duration is required") Integer duration,
                           @NotBlank(message = "Language is required") String language,
                           @NotBlank(message = "Release Date is required") Date releaseDate) {
}
