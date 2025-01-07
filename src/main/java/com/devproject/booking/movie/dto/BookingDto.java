package com.devproject.booking.movie.dto;

import com.devproject.booking.movie.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private UserDto userDto;
    private ShowDto showDto;
    private List<SeatDto> seats;
    private PaymentStatus paymentStatus;
    private LocalDateTime bookingTime;
    private BigDecimal totalPrice;
}
