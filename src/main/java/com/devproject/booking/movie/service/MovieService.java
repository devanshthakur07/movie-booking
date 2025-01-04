package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.MovieRequest;
import com.devproject.booking.movie.entity.Movie;
import com.devproject.booking.movie.entity.Theater;
import com.devproject.booking.movie.repository.MovieRepository;
import com.devproject.booking.movie.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, TheaterRepository theaterRepository) {
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }


    public Movie saveMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setDuration(movieRequest.duration());
        movie.setGenre(movieRequest.genre());
        movie.setLanguage(movieRequest.language());
        movie.setTitle(movieRequest.title());
        movie.setReleaseDate(new Date());
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie addTheaterToMovie(Long movieId, Long theaterId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        movie.getTheaters().add(theater);
        return movieRepository.save(movie);
    }

    public Movie removeTheaterFromMovie(Long movieId, Long theaterId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        movie.getTheaters().remove(theater);
        return movieRepository.save(movie);
    }
}
