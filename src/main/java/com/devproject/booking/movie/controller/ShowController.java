package com.devproject.booking.movie.controller;

import com.devproject.booking.movie.dto.ShowDto;
import com.devproject.booking.movie.dto.request.ShowRequest;
import com.devproject.booking.movie.entity.Show;
import com.devproject.booking.movie.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping("/{movieId}/{theatreId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Show> createOrUpdateShow(
            @PathVariable Long movieId,
            @PathVariable Long theatreId,
            @Valid @RequestBody ShowRequest showRequest) {
        Show savedShow = showService.createOrUpdateShow(movieId, theatreId, showRequest);
        return ResponseEntity.ok(savedShow);
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable Long id) {
        Show show = showService.getShowById(id);
        return ResponseEntity.ok(show);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Show>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Show>> getShowsByTheater(@PathVariable Long theaterId) {
        return ResponseEntity.ok(showService.getShowsByTheater(theaterId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.ok("Show deleted successfully");
    }
}
