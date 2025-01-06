package com.devproject.booking.movie.service;

import com.devproject.booking.movie.entity.Seat;
import com.devproject.booking.movie.entity.SeatStatus;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public void bookSeats(Show show, List<String> seatNumbers) {
        for (String seatNumber : seatNumbers) {
            Seat seat = seatRepository.findByShowAndSeatNumber(show, seatNumber)
                    .orElseThrow(() -> new RuntimeException("Seat not found: " + seatNumber));
            if (seat.getStatus() == SeatStatus.BOOKED) {
                throw new RuntimeException("Seat already booked: " + seatNumber);
            }
            seat.setStatus(SeatStatus.BOOKED);
            seatRepository.save(seat);
        }
    }
}
