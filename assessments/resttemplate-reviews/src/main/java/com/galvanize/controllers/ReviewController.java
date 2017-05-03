package com.galvanize.controllers;

import com.galvanize.models.MovieResult;
import com.galvanize.models.MovieReview;
import com.galvanize.models.Review;
import com.galvanize.models.ReviewRequest;
import com.galvanize.repositories.ReviewRepository;
import com.galvanize.services.MoviesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("reviews")
    public MovieReview createReview(@RequestBody ReviewRequest reviewRequest) throws Exception {
        MoviesService moviesService = new MoviesService();
        MovieReview movieReview = new MovieReview();
        Review review = new Review(reviewRequest);
        MovieResult movie = moviesService.forTitleYear(reviewRequest.getTitle(), reviewRequest.getYear());

        if (movie != null) {
            review.setMovieId(movie.getId());
            this.reviewRepository.save(review);
            movieReview.setMovie(movie);
            return movieReview;
        }

        movie = new MovieResult();
        movie.setTitle(reviewRequest.getTitle());
        movie.setYear(reviewRequest.getYear());

        MovieResult result = moviesService.postMovie(movie);
        review.setMovieId(movie.getId());
        this.reviewRepository.save(review);
        movieReview.setReview(review);
        movieReview.setMovie(movie);

        return movieReview;
    }

}
