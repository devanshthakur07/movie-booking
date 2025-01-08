package com.devproject.booking.movie.controller;

import com.devproject.booking.movie.dto.BookingDto;
import com.devproject.booking.movie.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<BookingDto>> getUserBookings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<BookingDto> bookings = bookingService.getUserBookings(username);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @RequestParam Long showId,
            @RequestBody List<String> seatNumbers) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BookingDto booking = bookingService.createBooking(authentication.getName(), showId, seatNumbers);
        return ResponseEntity.ok(booking);
    }
}
