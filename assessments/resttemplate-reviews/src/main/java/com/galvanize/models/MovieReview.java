package com.galvanize.models;

public class MovieReview {
    private MovieResult movie;

    private Review review;

    public MovieResult getMovie() {
        return movie;
    }

    public void setMovie(MovieResult movie) {
        this.movie = movie;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}
