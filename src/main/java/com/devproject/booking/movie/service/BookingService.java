package com.devproject.booking.movie.service;

import com.devproject.booking.movie.entity.Booking;
import com.devproject.booking.movie.entity.PaymentStatus;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;


    private final SeatService seatService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, SeatService seatService) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
    }

    @Transactional
    public Booking createBooking(User user, Show show, List<String> seatNumbers) {
        seatService.bookSeats(show, seatNumbers);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeats(String.join(",", seatNumbers));
        booking.setPaymentStatus(PaymentStatus.PENDING);
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUser(user);
    }
}
