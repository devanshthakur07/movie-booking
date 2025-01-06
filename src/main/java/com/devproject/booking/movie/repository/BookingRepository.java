package com.devproject.booking.movie.repository;

import com.devproject.booking.movie.entity.Booking;
import com.devproject.booking.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}
