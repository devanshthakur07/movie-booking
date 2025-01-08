package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.BookingDto;
import com.devproject.booking.movie.entity.Booking;
import com.devproject.booking.movie.entity.PaymentStatus;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.entity.User;
import com.devproject.booking.movie.exception.ShowNotFoundException;
import com.devproject.booking.movie.exception.UserNotFoundException;
import com.devproject.booking.movie.repository.BookingRepository;
import com.devproject.booking.movie.repository.ShowRepository;
import com.devproject.booking.movie.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, SeatService seatService, ModelMapper modelMapper, UserRepository userRepository, ShowRepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    @Transactional
    public BookingDto createBooking(String username, Long showId, List<String> seatNumbers) {

        User user = findUserByUsername(username);
        Show show = findShowById(showId);
        BigDecimal totalBookingPrice = seatService.bookSeats(show, seatNumbers);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setSeats(String.join(",", seatNumbers));
        booking.setPaymentStatus(PaymentStatus.PENDING);
        booking.setTotalPrice(totalBookingPrice);
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingDto.class);
    }

    private Show findShowById(Long showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new ShowNotFoundException("Show with id" + showId + "not found"));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->new UserNotFoundException("User with username" + username + "not found"));
    }

    public List<BookingDto> getUserBookings(String username) {

        User user = findUserByUsername(username);
        return bookingRepository.findByUser(user)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDto.class))
                .collect(Collectors.toList());
    }
}
