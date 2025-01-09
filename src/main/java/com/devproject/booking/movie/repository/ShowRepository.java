package com.devproject.booking.movie.repository;

import com.devproject.booking.movie.entity.Show;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovieId(Long movieId);
    List<Show> findByTheaterId(Long theaterId);

    List<Show> findByTheaterIdAndScreenNumber(Long theaterId, Integer screenNumber);
}
