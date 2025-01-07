package com.devproject.booking.movie.controller;

import com.devproject.booking.movie.dto.request.TheaterRequest;
import com.devproject.booking.movie.entity.Theater;
import com.devproject.booking.movie.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Theater> createTheater(@Valid @RequestBody TheaterRequest theaterRequest) {
        Theater savedTheater = theaterService.saveTheater(theaterRequest);
        return ResponseEntity.ok(savedTheater);
    }

    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheatres() {
        List<Theater> theatres = theaterService.getAllTheaters();
        return ResponseEntity.ok(theatres);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Theater> updateTheatre(@PathVariable Long id, @Valid @RequestBody TheaterRequest theaterRequest) {
        Theater updatedTheater = theaterService.updateTheater(id, theaterRequest);
        return ResponseEntity.ok(updatedTheater);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTheatre(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.ok("Theater deleted successfully");
    }
}
