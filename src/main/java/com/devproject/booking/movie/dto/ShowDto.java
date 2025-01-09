package com.devproject.booking.movie.dto;

import com.devproject.booking.movie.entity.Theater;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDto{
    private MovieDto movie;
    private TheaterDto theater;
    private Integer screenNumber;
    private LocalDateTime showTime;
}
