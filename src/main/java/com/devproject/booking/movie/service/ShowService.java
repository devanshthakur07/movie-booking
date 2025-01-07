package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.ShowDto;
import com.devproject.booking.movie.dto.request.ShowRequest;
import com.devproject.booking.movie.entity.Movie;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.entity.Theater;
import com.devproject.booking.movie.repository.MovieRepository;
import com.devproject.booking.movie.repository.ShowRepository;
import com.devproject.booking.movie.repository.TheaterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private final ShowRepository showRepository;

    private final MovieRepository movieRepository;

    private final TheaterRepository theatreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, TheaterRepository theatreRepository, ModelMapper modelMapper) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.modelMapper = modelMapper;
    }

    public Show createOrUpdateShow(Long movieId, Long theaterId, ShowRequest showRequest) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Theater theater = theatreRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        Show show = new Show();

        show.setShowTime(showRequest.showTime());
        show.setScreenNumber(showRequest.screenNumber());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    public List<ShowDto> getAllShows() {
        return showRepository.findAll()
                .stream()
                .map(show -> modelMapper.map(show, ShowDto.class))
                .collect(Collectors.toList());
    }

    public Show getShowById(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }

    public List<Show> getShowsByMovie(Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public List<Show> getShowsByTheater(Long theatreId) {
        return showRepository.findByTheaterId(theatreId);
    }


}