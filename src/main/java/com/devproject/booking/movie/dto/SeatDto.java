package com.devproject.booking.movie.dto;

import com.devproject.booking.movie.entity.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private String seatNumber;
    private SeatStatus status;
    private String seatType;
    private BigDecimal seatPrice;
}
