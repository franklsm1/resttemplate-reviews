package com.galvanize.models;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long movieId;
    private String reviewer;

    @Column(columnDefinition = "TEXT")
    private String comment;
    private Double starRating;

    public Review() {

    }

    public Review(ReviewRequest reviewRequest) {
        reviewer = reviewRequest.getReviewer();
        comment = reviewRequest.getComment();
        starRating = reviewRequest.getStarRating();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getStarRating() {
        return starRating;
    }

    public void setStarRating(Double starRating) {
        this.starRating = starRating;
    }
}
