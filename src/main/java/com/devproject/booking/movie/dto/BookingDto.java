package com.devproject.booking.movie.dto;

import com.devproject.booking.movie.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private UserDto userDto;
    private ShowDto showDto;
    private String seats;
    private PaymentStatus paymentStatus;
    private LocalDateTime bookingTime;
}
