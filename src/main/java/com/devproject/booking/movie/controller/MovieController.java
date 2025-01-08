package com.devproject.booking.movie.controller;

import com.devproject.booking.movie.dto.MovieDto;
import com.devproject.booking.movie.dto.request.MovieRequest;
import com.devproject.booking.movie.entity.Movie;
import com.devproject.booking.movie.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Movie> createOrUpdateMovie(@Valid @RequestBody MovieRequest movieRequest) {
        Movie savedMovie = movieService.saveMovie(movieRequest);
        return ResponseEntity.ok(savedMovie);
    }

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        MovieDto movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{movieId}/add-theater/{theaterId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> addTheaterToMovie(@PathVariable Long movieId, @PathVariable Long theaterId) {
        Movie updatedMovie = movieService.addTheaterToMovie(movieId, theaterId);
        return ResponseEntity.ok(updatedMovie);
    }

    @PostMapping("/{movieId}/remove-theater/{theaterId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> removeTheaterFromMovie(@PathVariable Long movieId, @PathVariable Long theaterId) {
        Movie updatedMovie = movieService.removeTheaterFromMovie(movieId, theaterId);
        return ResponseEntity.ok(updatedMovie);
    }
}
