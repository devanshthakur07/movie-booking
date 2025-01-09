package com.devproject.booking.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDto{
    private Long id;
    private MovieDto movie;
    private TheaterDto theater;
    private Integer screenNumber;
    private LocalDateTime showTime;
}
