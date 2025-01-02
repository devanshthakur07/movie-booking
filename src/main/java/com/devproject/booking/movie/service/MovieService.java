package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.MovieRequest;
import com.devproject.booking.movie.entity.Movie;
import com.devproject.booking.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
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
}
