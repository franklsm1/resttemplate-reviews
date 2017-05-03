package com.galvanize.controllers;

import com.galvanize.models.MovieResult;
import com.galvanize.services.MoviesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {
    private MoviesService moviesService;

    MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping("/movies/find")
    public MovieResult getMovie(@RequestParam String title, @RequestParam int year) {
        return moviesService.forTitleYear(title, year);
    }
}
