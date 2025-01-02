package com.devproject.booking.movie.dto;

import java.util.Date;

public record MovieRequest(String title, String genre, Integer duration, String language, Date releaseDate) {
}
