package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.MovieDto;
import com.devproject.booking.movie.dto.request.MovieRequest;
import com.devproject.booking.movie.entity.Movie;
import com.devproject.booking.movie.exception.MovieNotFoundException;
import com.devproject.booking.movie.repository.MovieRepository;
import com.devproject.booking.movie.repository.TheaterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieService(MovieRepository movieRepository, TheaterRepository theaterRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }


    public MovieDto saveMovie(MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setDuration(movieRequest.duration());
        movie.setGenre(movieRequest.genre());
        movie.setLanguage(movieRequest.language());
        movie.setTitle(movieRequest.title());
        movie.setReleaseDate(new Date());
        movieRepository.save(movie);

        return modelMapper.map(movie, MovieDto.class);
    }

    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }

    public MovieDto getMovieById(Long id) {
        Movie movie =  movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie with id " + id + "not found"));
        return modelMapper.map(movie, MovieDto.class);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
