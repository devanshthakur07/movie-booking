package com.devproject.booking.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private String title;
    private String genre;
    private Integer duration;
    private String language;
    private Date releaseDate;
}
