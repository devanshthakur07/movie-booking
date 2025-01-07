package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.SeatDto;
import com.devproject.booking.movie.entity.Seat;
import com.devproject.booking.movie.entity.SeatStatus;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getAvailableSeats(Show show) {
        return seatRepository.findByShowAndStatus(show, SeatStatus.AVAILABLE);
    }

    public BigDecimal bookSeats(Show show, List<String> seatNumbers) {
        List<Seat> seatsToBook = new ArrayList<>();

        for (String seatNumber : seatNumbers) {
            Seat seat = seatRepository.findByShowAndSeatNumber(show, seatNumber)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatNumber));
            if (seat.getStatus() == SeatStatus.BOOKED) {
                throw new RuntimeException("Seat already booked: " + seatNumber);
            }
            seatsToBook.add(seat);
        }

        for (Seat seat : seatsToBook) {
            seat.setStatus(SeatStatus.BOOKED);
            seatRepository.save(seat);
        }

        return calculateTotalPrice(seatsToBook);

    }

    public BigDecimal calculateTotalPrice(List<Seat> seats) {
        return seats.stream()
                .map(Seat::getSeatPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<SeatDto> getSeatDetails(Long showId, List<String> seatNumbers) {
        List<Seat> seats = seatRepository.findByShowIdAndSeatNumberIn(showId, seatNumbers);

        // Map to SeatDto
        return seats.stream()
                .map(seat -> new SeatDto(
                        seat.getSeatNumber(),
                        seat.getStatus(),
                        seat.getSeatType(),
                        seat.getSeatPrice()))
                .collect(Collectors.toList());
    }
}
