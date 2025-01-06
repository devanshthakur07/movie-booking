package com.devproject.booking.movie.controller;

import com.devproject.booking.movie.entity.Booking;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.repository.ShowRepository;
import com.devproject.booking.movie.repository.UserRepository;
import com.devproject.booking.movie.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final ShowRepository showRepository;

    private final UserRepository userRepository;

    @Autowired
    public BookingController(BookingService bookingService, ShowRepository showRepository, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<List<Booking>> getUserBookings(@AuthenticationPrincipal UserDetails userDetails) {
        // Extract the username from the current user
        String username = userDetails.getUsername();

        // Fetch the user entity
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch bookings for the user
        List<Booking> bookings = bookingService.getUserBookings(user);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long showId,
            @RequestBody List<String> seatNumbers) {


        String username = userDetails.getUsername();

        // Fetch the user entity
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the show entity
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Create the booking
        Booking booking = bookingService.createBooking(user, show, seatNumbers);
        return ResponseEntity.ok(booking);
    }
}
