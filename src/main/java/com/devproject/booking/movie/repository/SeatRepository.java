package com.devproject.booking.movie.repository;

import com.devproject.booking.movie.entity.Seat;
import com.devproject.booking.movie.entity.SeatStatus;
import com.devproject.booking.movie.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowAndStatus(Show show, SeatStatus status);

    Optional<Seat> findByShowAndSeatNumber(Show show, String seatNumber);
}
