package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.BookingDto;
import com.devproject.booking.movie.entity.Booking;
import com.devproject.booking.movie.entity.PaymentStatus;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final SeatService seatService;

    private final ModelMapper modelMapper;

    @Autowired
    public BookingService(BookingRepository bookingRepository, SeatService seatService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.modelMapper = modelMapper;
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

    public List<BookingDto> getUserBookings(User user) {
        return bookingRepository.findByUser(user)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDto.class))
                .collect(Collectors.toList());
    }
}
