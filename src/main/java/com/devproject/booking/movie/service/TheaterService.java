package com.devproject.booking.movie.service;

import com.devproject.booking.movie.dto.request.TheaterRequest;
import com.devproject.booking.movie.entity.Theater;
import com.devproject.booking.movie.exception.CustomDuplicateException;
import com.devproject.booking.movie.repository.TheaterRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TheaterService(TheaterRepository theaterRepository, ModelMapper modelMapper) {
        this.theaterRepository = theaterRepository;
        this.modelMapper = modelMapper;
    }


    public Theater saveTheater(@Valid TheaterRequest theaterRequest) {
        Theater theater = new Theater();
        theater.setCity(theaterRequest.city());
        theater.setName(theaterRequest.name());
        theater.setScreens(theaterRequest.screens());
        try {
            return theaterRepository.save(theater);
        }
        catch (DataIntegrityViolationException ex) {
            throw new CustomDuplicateException("Theater with same name and city already exists!");
        }
    }

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public Theater updateTheater(Long id, TheaterRequest theaterRequest) {
        Theater Theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found"));
        Theater.setName(theaterRequest.name());
        Theater.setCity(theaterRequest.city());
        Theater.setScreens(theaterRequest.screens());
        return theaterRepository.save(Theater);
    }

    public void deleteTheater(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new RuntimeException("Theater not found");
        }
        theaterRepository.deleteById(id);
    }
}
